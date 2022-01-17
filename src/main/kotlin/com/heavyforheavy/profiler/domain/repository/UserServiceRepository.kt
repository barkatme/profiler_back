package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.model.UserService

interface UserServiceRepository {
  suspend fun getById(id: Int): UserService?
  suspend fun getByEmail(email: String): List<UserService>
  suspend fun getByUserId(userId: Int): List<UserService>
  suspend fun addService(userId: Int, serviceId: Int, link: String): UserService
  suspend fun updateServiceLink(userService: UserService): UserService
  suspend fun deleteUserService(id: Int): Int
}