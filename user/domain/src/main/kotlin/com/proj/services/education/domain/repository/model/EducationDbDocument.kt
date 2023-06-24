package com.proj.services.education.domain.repository.model

import com.proj.services.user.api.UserEducationHistory
import org.springframework.data.mongodb.core.mapping.Document

@Document("userEducationHistory")
data class EducationDbDocument(val publicId: String, val userId: String, val institutionId: String, val degree: String) {
    fun toEducationHistory() = UserEducationHistory(publicId, userId, institutionId, degree)
}