package com.heavyforheavy.profiler.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UserRolePermissions : Table("USER_ROLE_PERMISSIONS") {

  val id: Column<Int> = integer("id")
    .autoIncrement()
    .primaryKey()

  val userRoleId: Column<Int> =
    UserRolePermissions.integer("user_role_id")
      .references(UserRoles.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)

  val permissionId: Column<Int> =
    UserRolePermissions.integer("permission_id")
      .references(Permissions.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)

  val createdAt: Column<DateTime> = datetime("created_at")

}