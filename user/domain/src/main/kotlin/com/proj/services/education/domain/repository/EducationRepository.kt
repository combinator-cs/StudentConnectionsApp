package com.proj.services.education.domain.repository

import com.proj.services.user.api.UserEducationHistory
import com.mongodb.client.result.DeleteResult

interface EducationRepository {
    suspend fun findByUserId(userId: String) : List<UserEducationHistory>
    suspend fun findByPublicId(publicId: String) : UserEducationHistory
    suspend fun addEducation(educationHistory: UserEducationHistory): UserEducationHistory
    suspend fun updateEducationRecord(educationHistory: UserEducationHistory): UserEducationHistory
    suspend fun deleteEducationRecord(publicId: String): DeleteResult
    suspend fun findUserIdsByInstitute(institutionId: String): List<String>
}