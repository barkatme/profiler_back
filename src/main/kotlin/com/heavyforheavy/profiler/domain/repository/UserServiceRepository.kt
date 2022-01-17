package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.model.UserService

interface UserServiceRepository {
    suspend fun get(id: Int): UserService?
    suspend fun getByEmail(email: String): List<UserService>
    suspend fun getByUserId(userId: Int): List<UserService>
    suspend fun addService(userId: Int, serviceId: Int, link: String): Int
    suspend fun updateServiceLink(userService: UserService): Int
    suspend fun deleteUserService(id: Int): Int
}