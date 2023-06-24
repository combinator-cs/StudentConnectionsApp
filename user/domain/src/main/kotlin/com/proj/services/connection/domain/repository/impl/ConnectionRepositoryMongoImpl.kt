package com.proj.services.connection.domain.repository.impl

import com.proj.services.connection.domain.repository.ConnectionRepository
import com.proj.services.connection.domain.repository.model.ConnectionDBDocument
import com.proj.services.user.api.Connection
import com.proj.services.user.api.User
import com.proj.services.user.domain.repository.model.UserDbDocument
import io.github.oshai.kotlinlogging.KLoggable
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query

class ConnectionRepositoryMongoImpl(
        private val mongoOperations: ReactiveMongoOperations,
) : KLoggable, ConnectionRepository {
    override val logger = logger()

    override suspend fun addNewConnection(connection: Connection): Connection {
        return this.mongoOperations.save(connection).awaitLast()
    }

    override suspend fun getConnectionsByUser(userId: String): List<String> {
        val query = Query(Criteria().orOperator(where("userId1").`is`(userId), where("userId2").`is`(userId)))
        val result = ArrayList<String>()
        this.mongoOperations.find(query, ConnectionDBDocument::class.java).map {
            if (it.userId1 == userId) {
                result.add(it.userId2)
            } else {
                result.add(it.userId1)
            }
        }.awaitLast()
        return result
    }

    override suspend fun findAll(): List<Connection> {
        val query = Query()
        val result = ArrayList<Connection>()
        this.mongoOperations.find(query, ConnectionDBDocument::class.java).map { result.add(it.toConnection()) }.awaitLast()
        return result
    }
}