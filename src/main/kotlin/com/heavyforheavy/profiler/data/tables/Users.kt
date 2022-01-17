package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime


object Users : Table("USERS") {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val deleted: Column<Boolean> = bool("deleted")
    val active: Column<Boolean?> = bool("active").nullable()
    val email: Column<String> = varchar("email", 100)
    val passwordHash: Column<String> = varchar("password_hash", 100)
    val userRoleId: Column<Int> = integer("user_role_id")
    val login: Column<String?> = varchar("login", 100).nullable()
    val token: Column<String?> = varchar("token", 255).nullable()
    val first_name: Column<String?> = varchar("first_name", 100).nullable()
    val last_name: Column<String?> = varchar("last_name", 100).nullable()
    val about: Column<String?> = varchar("about", 100).nullable()
    val date_registration: Column<DateTime> = datetime("created_at")
}