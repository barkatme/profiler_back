package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asPermission
import com.heavyforheavy.profiler.data.entity.asPermissionEntity
import com.heavyforheavy.profiler.data.tables.Permissions
import com.heavyforheavy.profiler.data.tables.UrlPermissions
import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.model.Permission
import com.heavyforheavy.profiler.model.exception.DatabaseException
import org.jetbrains.exposed.sql.*

class HerokuPermissionRepository : PermissionRepository {

  override suspend fun getAll(): List<Permission> = dbQuery {
    Permissions.selectAll()
      .mapNotNull { it.asPermission() }
  }

  override suspend fun getById(id: Int): Permission? = dbQuery {
    Permissions.select { Permissions.id eq id }
      .mapNotNull { it.asPermission() }
      .singleOrNull()
  }

  override suspend fun getUrlPermissions(url: String): List<Permission> = dbQuery {
    (UrlPermissions innerJoin Permissions).slice(
      Permissions.id,
      Permissions.name,
      Permissions.createdAt
    ).select {
      (UrlPermissions.url eq url) and (UrlPermissions.permissionId eq Permissions.id)
    }
      .mapNotNull { it.asPermission() }
  }

  override suspend fun insert(permission: Permission): Permission = dbQuery {
    permission.asPermissionEntity().name?.let { permissionName ->
      Permissions.insert { table ->
        table[name] = permissionName
      }.resultedValues?.first()
    }?.asPermission() ?: throw DatabaseException.OperationFailed("permission name invalid")
  }

  override suspend fun update(permission: Permission): Permission {
    val entity = permission.asPermissionEntity()
    dbQuery {
      Permissions.update(where = { Permissions.id eq permission.id }) { table ->
        entity.name?.let { table[name] = entity.name }
        //TODO should include field such as "updated_at" instead
        //table[createdAt] = DateTime.now()
      }
    }
    return getById(entity.id) ?: throw DatabaseException.OperationFailed(
      "updated permission not found"
    )
  }

  override suspend fun delete(permission: Permission): Int = dbQuery {
    Permissions.deleteWhere {
      Permissions.id eq permission.asPermissionEntity().id
    }
  }

  override suspend fun delete(id: Int): Int = dbQuery {
    Permissions.deleteWhere {
      Permissions.id eq id
    }
  }

  override suspend fun deleteAll(): Int = dbQuery {
    Permissions.deleteAll()
  }
}