package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UserServices : Table("USER_SERVICE"){
    val id: Column<Int> = UserServices.integer("id").autoIncrement().primaryKey()
    val userId: Column<Int> = UserServices.integer("user_id").references(Users.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val serviceId: Column<Int> = UserServices.integer("service_id").references(ServiceInfos.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val link: Column<String> = UserServices.varchar("link", 100)
    val createdAt: Column<DateTime> = UserServices.datetime("created_at")
}