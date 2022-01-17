package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object Permissions : Table("PERMISSIONS") {
  val id: Column<Int> = integer("id").autoIncrement().primaryKey()
  val name: Column<String> = varchar("name", 100)
  val createdAt: Column<DateTime> = datetime("created_at")
}