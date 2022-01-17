package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.model.UserService
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetServicesUseCase(
  private val serviceRepository: UserServiceRepository,
  private val userRepository: UserRepository
) {

  @Suppress("MemberVisibilityCanBePrivate")
  suspend fun getServices(userId: Int): List<UserService> = withContext(Dispatchers.IO) {
    serviceRepository.getByUserId(userId)
  }

  suspend fun getServices(userEmail: String?): List<UserService> = withContext(Dispatchers.IO) {
    val user = userEmail?.let { userRepository.getByEmail(it) }
      ?: throw AuthException.InvalidEmail()
    getServices(user.id)
  }
}