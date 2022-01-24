package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.infrastructure.model.Permission

interface PermissionRepository {
  suspend fun getAll(): List<Permission>
  suspend fun getById(id: Int): Permission?
  suspend fun getUrlPermissions(url: String): List<Permission>
  suspend fun addUrlPermission(url: String, permission: Permission): Int
  suspend fun deleteUrlPermission(url: String, permission: Permission): Int
  suspend fun insert(permission: Permission): Permission
  suspend fun update(permission: Permission): Permission
  suspend fun delete(permission: Permission): Int
  suspend fun delete(id: Int): Int
  suspend fun deleteAll(): Int
}