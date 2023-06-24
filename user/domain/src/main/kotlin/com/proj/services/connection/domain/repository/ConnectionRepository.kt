package com.proj.services.connection.domain.repository

import com.proj.services.user.api.Connection

interface ConnectionRepository {
    suspend fun addNewConnection(connection: Connection): Connection
    suspend fun getConnectionsByUser(userId: String): List<String>
    suspend fun findAll(): List<Connection>
}