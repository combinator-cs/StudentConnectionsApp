package com.proj.services.connection.domain.repository.model

import com.proj.services.user.api.Connection
import org.springframework.data.mongodb.core.mapping.Document

@Document("connection")
data class ConnectionDBDocument(val connectionId: String, val userId1: String, val userId2: String) {
    fun toConnection() = Connection(connectionId, userId1, userId2)
}