package com.proj.services.user.domain.repository.model

import com.proj.services.user.api.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
data class UserDbDocument(val userId: String, val name: String?, val email: String?) {
    fun toUser() = User(userId = this.userId, name = this.name, email = this.email)
}