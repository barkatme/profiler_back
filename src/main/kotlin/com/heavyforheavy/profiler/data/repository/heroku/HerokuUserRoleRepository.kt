package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asPermission
import com.heavyforheavy.profiler.data.entity.asPermissionEntity
import com.heavyforheavy.profiler.data.entity.asUserRole
import com.heavyforheavy.profiler.data.entity.asUserRoleEntity
import com.heavyforheavy.profiler.data.tables.Permissions
import com.heavyforheavy.profiler.data.tables.UserRolePermissions
import com.heavyforheavy.profiler.data.tables.UserRoles
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.infrastructure.model.Permission
import com.heavyforheavy.profiler.infrastructure.model.Role
import com.heavyforheavy.profiler.infrastructure.model.exception.DatabaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime


class HerokuUserRoleRepository : RoleRepository {

  override suspend fun getAll(): List<Role> = dbQuery {
    UserRoles.selectAll().mapNotNull { it.asUserRole() }
  }

  override suspend fun getById(id: Int): Role? = dbQuery {
    UserRoles.select { UserRoles.id eq id }
      .mapNotNull { it.asUserRole() }
      .singleOrNull()
  }

  override suspend fun getPermissions(userRoleId: Int?): List<Permission> = dbQuery {
    if (userRoleId == null) {
      return@dbQuery emptyList()
    }

    (UserRolePermissions innerJoin Permissions).slice(
      Permissions.id,
      Permissions.name,
      Permissions.createdAt
    )
      .select { (UserRolePermissions.userRoleId eq userRoleId) and (UserRolePermissions.permissionId eq Permissions.id) }
      .mapNotNull { it.asPermission() }
  }

  override suspend fun getPermissions(role: Role): List<Permission> = getPermissions(role.id)

  override suspend fun checkPermission(role: Role, permission: Permission): Boolean =
    withContext(Dispatchers.IO) {
      getPermissions(role.id).contains(permission)
    }

  override suspend fun insert(role: Role): Role = dbQuery {
    val entity = role.asUserRoleEntity()
    UserRoles.insert { table ->
      entity.name?.let { table[name] = it }
    }.resultedValues?.firstOrNull()?.asUserRole() ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun update(role: Role): Role {
    val entity = role.asUserRoleEntity()
    dbQuery {
      UserRoles.update(where = { UserRoles.id eq role.id }) { table ->
        entity.name?.let { table[name] = it }
        table[createdAt] = DateTime.now()
      }
    }
    return getById(entity.id) ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun delete(role: Role): Int = dbQuery {
    Permissions.deleteWhere {
      Permissions.id eq role.id
    }
  }

  override suspend fun delete(id: Int): Int = dbQuery {
    UserRoles.deleteWhere {
      UserRoles.id eq id
    }
  }

  override suspend fun addPermission(role: Role, permission: Permission): Int = dbQuery {
    val userRoleEntity = role.asUserRoleEntity()
    val permissionEntity = permission.asPermissionEntity()
    UserRolePermissions.insert {
      it[userRoleId] = userRoleEntity.id
      it[permissionId] = permissionEntity.id
    } get UserRolePermissions.id
  }

  override suspend fun removePermission(role: Role, permission: Permission): Int = dbQuery {
    val userRoleEntity = role.asUserRoleEntity()
    val permissionEntity = permission.asPermissionEntity()
    UserRolePermissions.deleteWhere {
      UserRolePermissions.userRoleId eq userRoleEntity.id
      UserRolePermissions.permissionId eq permissionEntity.id
    }
  }

  override suspend fun deleteAll(): Int = dbQuery {
    UserRoles.deleteAll()
  }

}