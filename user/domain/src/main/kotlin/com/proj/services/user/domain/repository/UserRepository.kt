package com.proj.services.user.domain.repository

import com.proj.services.user.api.User

interface UserRepository {
    suspend fun findByUserId(userId: String) : User
    suspend fun addNewUser(user: User): User
    suspend fun findUsersByIds(userIds: List<String>, sortBy: String?, sortDirection: String?, page: Int?, pageSize: Int?): List<User>
}