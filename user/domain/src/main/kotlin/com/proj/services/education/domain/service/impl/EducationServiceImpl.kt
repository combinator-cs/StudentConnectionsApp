package com.proj.services.education.domain.service.impl

import com.proj.services.connection.domain.repository.ConnectionRepository
import com.proj.services.education.domain.repository.EducationRepository
import com.proj.services.education.domain.service.EducationService
import com.proj.services.user.api.ConnectionGraph
import com.proj.services.user.api.User
import com.proj.services.user.api.UserEducationHistory
import com.proj.services.user.domain.repository.UserRepository
import java.util.*

class EducationServiceImpl(private val educationRepository: EducationRepository,
                           private val userRepository: UserRepository, private val connectionRepository: ConnectionRepository)
    : EducationService {

    override suspend fun getEducationRecordsByUserId(userId: String): List<UserEducationHistory> {
        return this.educationRepository.findByUserId(userId)
    }

    override suspend fun getEducationRecordsByPublicId(publicId: String): UserEducationHistory {
        return this.educationRepository.findByPublicId(publicId)
    }

    override suspend fun addEducation(education: UserEducationHistory): UserEducationHistory {
        education.publicId = UUID.randomUUID().toString()
        return this.educationRepository.addEducation(education)
    }

    override suspend fun updateEducationRecord(publicId: String, educationHistory: UserEducationHistory): UserEducationHistory {
        if (publicId == educationHistory.publicId) {
            return this.educationRepository.updateEducationRecord(educationHistory)
        } else {
            return educationHistory
        }
    }

    override suspend fun deleteEducationRecord(publicId: String): Boolean {
        return this.educationRepository.deleteEducationRecord(publicId).deletedCount > 0
    }

    override suspend fun getUsersForInstitute(institutionId: String, sortBy: String?, sortDirection: String?, page: Int?,
                                              pageSize: Int?): List<User> {
        val userIds = this.educationRepository.findUserIdsByInstitute(institutionId)
        return this.userRepository.findUsersByIds(userIds, sortBy, sortDirection, page, pageSize)
    }

    override suspend fun getUsersForInstituteByUser(institutionId: String, sortBy: String?, sortDirection: String?,
                                                    page: Int?, pageSize: Int?, userId: String): List<User>? {
        val users = getUsersForInstitute(institutionId, sortBy, sortDirection, page, pageSize)
        val connectionSet = HashSet(this.connectionRepository.getConnectionsByUser(userId))
        return users.sortedWith(compareBy<User> { !connectionSet.contains(it.userId) })
    }

    override suspend fun getUsersForInstituteByUserSubConnections(institutionId: String, sortBy: String?, sortDirection: String?,
                                                                  page: Int?, pageSize: Int?, userId: String): List<User>? {
        val users = getUsersForInstitute(institutionId, sortBy, sortDirection, page, pageSize)
        val connections = this.connectionRepository.findAll()
        val sortedConnections = ConnectionGraph().createGraph(connections).bfs(userId)
        return users.sortedBy {
            sortedConnections.getOrDefault(it.userId, Int.MAX_VALUE)
        }
    }
}