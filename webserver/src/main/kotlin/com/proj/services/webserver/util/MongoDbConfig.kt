package com.proj.services.webserver.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.mongodb.ConnectionString

@JsonIgnoreProperties(ignoreUnknown = true)
data class MongoDbConfig(
    val userName: String,
    val password: String,
    val connectionString: String,
    val dbName: String
) {
    fun toConnectionString(): ConnectionString {
        return ConnectionString(connectionString)
    }
}
