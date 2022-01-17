package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object SavedUsers : Table("SAVED_USERS") {
    val id: Column<Int> = SavedUsers.integer("id").autoIncrement().primaryKey()
    val userId: Column<Int> =
        SavedUsers.integer("user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val savedUserId: Column<Int> =
        SavedUsers.integer("saved_user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val createdAt: Column<DateTime> = SavedUsers.datetime("created_at")
}