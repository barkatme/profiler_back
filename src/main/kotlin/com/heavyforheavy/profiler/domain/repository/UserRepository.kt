package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.infrastructure.model.User

interface UserRepository {
  suspend fun getByEmail(email: String): User?
  suspend fun getById(id: Int): User?
  suspend fun insert(user: User): User
  suspend fun update(user: User): User
  suspend fun delete(user: User): Int
  suspend fun delete(id: Int): Int
  suspend fun count(): Int
  suspend fun recent(): List<User>
  suspend fun getByToken(token: String): User?
}