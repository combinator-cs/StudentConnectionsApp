package com.proj.services.user.domain.service.impl

import com.proj.services.connection.domain.repository.ConnectionRepository
import com.proj.services.user.api.Connection
import com.proj.services.user.api.User
import com.proj.services.user.domain.repository.UserRepository
import com.proj.services.user.domain.service.UserService
import java.util.*

class UserServiceImpl(private val userRepository: UserRepository, private val connectionRepository: ConnectionRepository) : UserService {
    override suspend fun getByUserId(userId: String): User {
        return this.userRepository.findByUserId(userId = userId)
    }

    override suspend fun addNewUser(user: User): User {
        user.userId = UUID.randomUUID().toString()
        return this.userRepository.addNewUser(user)
    }

    override suspend fun addUserConnection(connection: Connection): Connection {
        connection.connectionId = UUID.randomUUID().toString()
        return this.connectionRepository.addNewConnection(connection)
    }
}