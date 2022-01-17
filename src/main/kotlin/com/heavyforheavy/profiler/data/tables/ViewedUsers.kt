package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object ViewedUsers : Table("VIEWED_USER") {
    val id: Column<Int> = ViewedUsers.integer("id").autoIncrement().primaryKey()
    val userId: Column<Int> =
        ViewedUsers.integer("user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val viewedUserId: Column<Int> =
        ViewedUsers.integer("viewed_user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val lastTime: Column<DateTime> = ViewedUsers.datetime("last_time")
}