package com.proj.services.user.domain.service

import com.proj.services.user.api.Connection
import com.proj.services.user.api.User

interface UserService {
    suspend fun getByUserId(userId: String) : User
    suspend fun addNewUser(user: User): User
    suspend fun addUserConnection(connection: Connection): Connection
}