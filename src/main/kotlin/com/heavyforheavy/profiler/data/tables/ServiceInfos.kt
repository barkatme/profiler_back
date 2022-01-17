package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ServiceInfos : Table("SERVICE_INFO"){
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val link: Column<String> = varchar("link", 100)
    val name: Column<String> = varchar("name", 100)
    val image: Column<String> = varchar("image", 100)
}