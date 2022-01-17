package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.data.entity.asUserServiceEntity
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.model.UserService
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AddServiceUseCase(
  private val serviceRepository: UserServiceRepository,
  private val userRepository: UserRepository
) {

  @Suppress("MemberVisibilityCanBePrivate")
  suspend fun addService(userId: Int, userService: UserService): UserService =
    withContext(Dispatchers.IO) {
      val entity = userService.asUserServiceEntity()
      val userLink = entity.link
        ?: throw RequestException.MissedData("user's link")
      serviceRepository.addService(userId, entity.id, userLink)
    }

  suspend fun addService(userEmail: String?, userService: UserService): UserService =
    withContext(Dispatchers.IO) {
      val user = userEmail?.let { userRepository.getByEmail(it) }
        ?: throw AuthException.InvalidEmail()
      addService(user.id, userService)
    }
}