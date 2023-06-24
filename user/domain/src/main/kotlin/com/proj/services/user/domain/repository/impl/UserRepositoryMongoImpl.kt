package com.proj.services.user.domain.repository.impl

import com.proj.services.user.api.User
import com.proj.services.user.domain.repository.UserRepository
import com.proj.services.user.domain.repository.model.UserDbDocument
import io.github.oshai.kotlinlogging.KLoggable
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.data.mongodb.core.query.isEqualTo


class UserRepositoryMongoImpl(
        private val mongoOperations: ReactiveMongoOperations,
) : KLoggable, UserRepository {
    override val logger = logger()

    override suspend fun findByUserId(userId: String): User {
        val query = Query(UserDbDocument::userId isEqualTo userId)
        return this.mongoOperations.find(query, UserDbDocument::class.java).map { it.toUser() }.awaitLast()
    }

    override suspend fun addNewUser(user: User): User {
        return this.mongoOperations.save(user).awaitLast()
    }

    override suspend fun findUsersByIds(userIds: List<String>, sortBy: String?, sortDirection: String?, page: Int?,
                                        pageSize: Int?): List<User> {
        val result = ArrayList<User>()
        try {
            val query = Query(UserDbDocument::userId inValues userIds)
            if (sortBy != null && sortDirection != null) {
                if ("DESC".equals(sortDirection, ignoreCase = true)) {
                    query.with(Sort.by(Sort.Direction.DESC, sortBy))
                } else {
                    query.with(Sort.by(Sort.Direction.ASC, sortBy))
                }
            }
            if (page != null && pageSize != null) {
                query.with(PageRequest.of(page - 1, pageSize))
            }
            this.mongoOperations.find(query, UserDbDocument::class.java).map { result.add(it.toUser()) }.awaitLast()
            return result
        } catch (exception: Exception) {
            logger.error("Error finding Users by institution", exception)
        }
        return result
    }
}