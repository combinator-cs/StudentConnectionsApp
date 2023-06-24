package com.proj.services.education.domain.repository.impl

import com.proj.services.education.domain.repository.EducationRepository
import com.proj.services.education.domain.repository.model.EducationDbDocument
import com.proj.services.user.api.UserEducationHistory
import com.mongodb.client.result.DeleteResult
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo

class EducationMongoRepositoryImpl(private val mongoOperations: ReactiveMongoOperations) : EducationRepository {

    override suspend fun findByUserId(userId: String): List<UserEducationHistory> {
        val query = Query(EducationDbDocument::userId isEqualTo userId)
        val result = ArrayList<UserEducationHistory>()
        this.mongoOperations.find(query, EducationDbDocument::class.java).map { result.add(it.toEducationHistory()) }.awaitLast()
        return result
    }

    override suspend fun findByPublicId(publicId: String): UserEducationHistory {
        val query = Query(EducationDbDocument::publicId isEqualTo publicId)
        return this.mongoOperations.findOne(query, EducationDbDocument::class.java).map { it.toEducationHistory() }.awaitLast()
    }

    override suspend fun addEducation(educationHistory: UserEducationHistory): UserEducationHistory {
        return this.mongoOperations.save(educationHistory).awaitLast()
    }

    override suspend fun updateEducationRecord(educationHistory: UserEducationHistory): UserEducationHistory {
        val query = Query(EducationDbDocument::publicId isEqualTo educationHistory.publicId)
        val update = Update()
        update["userId"] = educationHistory.userId
        update["institutionId"] = educationHistory.institutionId
        update["degree"] = educationHistory.degree
        return this.mongoOperations.findAndModify(query, update, FindAndModifyOptions().returnNew(true),
                EducationDbDocument::class.java).map { it.toEducationHistory() }.awaitLast()
    }

    override suspend fun deleteEducationRecord(publicId: String): DeleteResult {
        val query = Query(EducationDbDocument::publicId isEqualTo publicId)
        return this.mongoOperations.remove(query, EducationDbDocument::class.java).awaitLast()
    }

    override suspend fun findUserIdsByInstitute(institutionId: String): List<String> {
        val query = Query(EducationDbDocument:: institutionId isEqualTo institutionId)
        val result = ArrayList<String>()
        this.mongoOperations.find(query, EducationDbDocument::class.java).map { result.add(it.userId) }.awaitLast()
        return result
    }

}