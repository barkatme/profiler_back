package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UrlPermissions : Table("URL_PERMISSIONS") {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val permissionId: Column<Int> =
        UrlPermissions.integer("permission_id").references(Permissions.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val url: Column<String> = varchar("url", 100)
    val createdAt: Column<DateTime> = datetime("created_at")
}