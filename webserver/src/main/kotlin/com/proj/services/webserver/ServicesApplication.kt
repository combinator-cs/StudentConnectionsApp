package com.proj.services.webserver

import com.proj.services.connection.domain.repository.ConnectionRepository
import com.proj.services.connection.domain.repository.impl.ConnectionRepositoryMongoImpl
import com.proj.services.education.domain.repository.EducationRepository
import com.proj.services.education.domain.repository.impl.EducationMongoRepositoryImpl
import com.proj.services.education.domain.service.EducationService
import com.proj.services.education.domain.service.impl.EducationServiceImpl
import com.proj.services.education.routing.EducationRoutingController
import com.proj.services.user.domain.repository.UserRepository
import com.proj.services.user.domain.repository.impl.UserRepositoryMongoImpl
import com.proj.services.user.domain.service.UserService
import com.proj.services.user.domain.service.impl.UserServiceImpl
import com.proj.services.user.routing.UserRoutingController
import com.proj.services.webserver.util.MongoDbConfig
import com.proj.services.webserver.util.MongoDbUtil
import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@SpringBootApplication
class ServicesApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ServicesApplication>(*args)
        }
    }

    /* ***********************************************
    *
    * Infra
    *
    * ***********************************************/

    @Bean
    fun getPrimaryMongoClient(
    ): MongoClient {
        return MongoDbUtil.createClient(
                MongoDbConfig(
                        "Create-User-In-Mongo-Atlas",
                        "password_test",
                        "mongodb+srv://cluster0.newsly-createdcluster-in-atlas.mongodb.net",
                        "create-db-in-mongo-atlas"
                )
        )
    }

    @Bean
    @Qualifier("user")
    fun getPrimaryMongoUserOperations(
            @Autowired mongoClient: MongoClient
    ): ReactiveMongoOperations {
        return ReactiveMongoTemplate(
                mongoClient,
                "dankUserDev"
        )
    }

    /* ***********************************************
    *
    * Repositories
    *
    * ***********************************************/

    @Bean
    fun getUserRepository(
            @Autowired @Qualifier("user") mongoOperations: ReactiveMongoOperations
    ): UserRepository {
        return UserRepositoryMongoImpl(
                mongoOperations = mongoOperations
        )
    }

    @Bean
    fun getEducationRepository(
            @Autowired @Qualifier("user") mongoOperations: ReactiveMongoOperations
    ): EducationRepository {
        return EducationMongoRepositoryImpl(
                mongoOperations = mongoOperations
        )
    }

    @Bean
    fun getConnectionRepository(
            @Autowired @Qualifier("user") mongoOperations: ReactiveMongoOperations
    ): ConnectionRepository {
        return ConnectionRepositoryMongoImpl(
                mongoOperations = mongoOperations
        )
    }

    /* ***********************************************
    *
    * Services
    *
    * ***********************************************/

    @Bean
    fun getUserService(
            @Autowired userRepository: UserRepository,
            @Autowired connectionRepository: ConnectionRepository
    ): UserService {
        return UserServiceImpl(
                userRepository = userRepository,
                connectionRepository = connectionRepository
        )
    }

    @Bean
    fun getEducationService(
            @Autowired educationRepository: EducationRepository,
            @Autowired userRepository: UserRepository,
            @Autowired connectionRepository: ConnectionRepository
    ): EducationService {
        return EducationServiceImpl(
                educationRepository = educationRepository,
                userRepository = userRepository,
                connectionRepository = connectionRepository
        )
    }

    /* ***********************************************
    *
    * Controllers
    *
    * ***********************************************/

    @Bean
    fun getUserRoutingController(
            @Autowired userService: UserService
    ): UserRoutingController {
        return UserRoutingController(
                userService = userService
        )
    }

    @Bean
    fun getEducationRoutingController(
            @Autowired educationService: EducationService
    ): EducationRoutingController {
        return EducationRoutingController(
                educationService = educationService
        )
    }
}






