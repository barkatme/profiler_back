package com.heavyforheavy.profiler.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


object DatabaseFactory {
  private val dbUrl = System.getenv("DB_URL")
    ?: "jdbc:postgresql://ec2-34-241-212-219.eu-west-1.compute.amazonaws.com:5432/d8nfpg506ntt78?sslmode=require"
  private val dbUser = System.getenv("DB_USER")
    ?: "swtyfeeumzyeox"
  private val dbPassword = System.getenv("DB_PASSWORD")
    ?: "8f3c599bd50baca7ac17f18072246cc38c1a0319de2d42bacc0b7babfe0fecd9"

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