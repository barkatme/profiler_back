package com.heavyforheavy.profiler.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


object DatabaseFactory {
  private val dbUrl = System.getenv("DB_URL")
    ?: "jdbc:postgresql://ec2-54-247-122-209.eu-west-1.compute.amazonaws.com:5432/d18uskgncuue97?sslmode=require"
  private val dbUser = System.getenv("DB_USER")
    ?: "atdvagtkqrbzpg"
  private val dbPassword = System.getenv("DB_PASSWORD")
    ?: "17a6cedde6f4b21c0dc13fec84bafa1cba107c2f6ee964b06a9e8a9a26382efd"

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