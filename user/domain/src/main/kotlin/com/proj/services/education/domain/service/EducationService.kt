package com.proj.services.education.domain.service

import com.proj.services.user.api.User
import com.proj.services.user.api.UserEducationHistory

interface EducationService {
    suspend fun getEducationRecordsByUserId(userId: String): List<UserEducationHistory>
    suspend fun getEducationRecordsByPublicId(publicId: String): UserEducationHistory
    suspend fun addEducation(education: UserEducationHistory): UserEducationHistory
    suspend fun updateEducationRecord(publicId: String, educationHistory: UserEducationHistory): UserEducationHistory
    suspend fun deleteEducationRecord(publicId: String): Boolean
    suspend fun getUsersForInstitute(institutionId: String, sortBy: String?, sortDirection: String?, page: Int?, pageSize: Int?): List<User>
    suspend fun getUsersForInstituteByUser(institutionId: String, sortBy: String?, sortDirection: String?, page: Int?, pageSize: Int?, userId: String): List<User>?
    suspend fun getUsersForInstituteByUserSubConnections(institutionId: String, sortBy: String?, sortDirection: String?, page: Int?, pageSize: Int?, userId: String): List<User>?
}