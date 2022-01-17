package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.model.UserService
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateServiceUseCase(
    private val serviceRepository: UserServiceRepository,
    private val userRepository: UserRepository
) {

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun updateService(userService: UserService) = withContext(Dispatchers.IO) {
        serviceRepository.updateServiceLink(userService)
    }

    suspend fun updateService(userEmail: String?, userService: UserService) = withContext(Dispatchers.IO) {
        val user = userEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidEmail()
        if (user.id != userService.userId) {
            throw RequestException.OperationFailed(message = "userId of service doesn't equals ")
        }
        updateService(userService)
    }
}