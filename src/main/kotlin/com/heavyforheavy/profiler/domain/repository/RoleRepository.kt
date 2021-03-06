package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.infrastructure.model.Permission
import com.heavyforheavy.profiler.infrastructure.model.Role

interface RoleRepository {

  suspend fun getAll(): List<Role>
  suspend fun getById(id: Int): Role?
  suspend fun insert(role: Role): Role
  suspend fun update(role: Role): Role
  suspend fun delete(role: Role): Int
  suspend fun addPermission(role: Role, permission: Permission): Int
  suspend fun getPermissions(userRoleId: Int?): List<Permission>
  suspend fun getPermissions(role: Role): List<Permission>
  suspend fun checkPermission(role: Role, permission: Permission): Boolean
  suspend fun removePermission(role: Role, permission: Permission): Int
  suspend fun delete(id: Int): Int
  suspend fun deleteAll(): Int
}