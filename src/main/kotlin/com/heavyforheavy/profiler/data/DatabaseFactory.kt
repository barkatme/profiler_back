package com.heavyforheavy.profiler.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {

    private const val dbUrl = "jdbc:postgresql://ec2-54-247-122-209.eu-west-1.compute.amazonaws.com:5432/d18uskgncuue97?sslmode=require"
    private const val dbUser = "atdvagtkqrbzpg"
    private const val dbPassword = "17a6cedde6f4b21c0dc13fec84bafa1cba107c2f6ee964b06a9e8a9a26382efd"

    fun init() {
        Database.connect(hikari())
        val flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPassword).load()
        flyway.migrate()
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

}

suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }

fun Query.limitAndOffset(limit: Int? = null, offset: Int? = null) {
    if (limit != null && offset != null) {
        limit(limit, offset)
    } else if (limit != null) {
        limit(limit)
    }
}