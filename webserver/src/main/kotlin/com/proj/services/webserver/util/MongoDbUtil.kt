package com.proj.services.webserver.util

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ReadConcern
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.WriteConcern
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients

object MongoDbUtil {
    fun createClient(
        mongoDbConfig: MongoDbConfig
    ): MongoClient {
//        val mongoLogger: Logger = Logger.getLogger("org.mongodb")
//        mongoLogger.level = Level.SEVERE
        val mongoCredential = MongoCredential.createCredential(
            mongoDbConfig.userName,
            "admin", // Manually setting default value, as it is a required parameter here
            mongoDbConfig.password.toCharArray()
        )

        return MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(mongoDbConfig.toConnectionString())
                .serverApi(
                    ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build()
                )
                .readConcern(ReadConcern.MAJORITY)
                // for stronger consistency, see https://docs.mongodb.com/manual/core/replica-set-rollbacks/#avoid-replica-set-rollbacks
                .writeConcern(WriteConcern.MAJORITY)
                .retryWrites(true)
                .credential(mongoCredential)
                .build()
        )
    }
}