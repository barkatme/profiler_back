package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserByTokenUseCase(private val userRepository: UserRepository) {
  suspend fun getUser(token: String): User = withContext(Dispatchers.IO) {
    userRepository.getByToken(token) ?: throw AuthException.InvalidToken()
  }
}