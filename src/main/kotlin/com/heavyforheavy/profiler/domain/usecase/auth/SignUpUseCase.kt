package com.heavyforheavy.profiler.domain.usecase.auth

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.exceptions.ExposedSQLException

class SignUpUseCase(private val userRepository: UserRepository) {

  suspend fun signUp(user: User, tokenGenerator: (User) -> String): User =
    withContext(Dispatchers.IO) {
      user.token = tokenGenerator(user)
      try {
        userRepository.insert(user)
      } catch (e: ExposedSQLException) {
        throw AuthException.EmailAlreadyExists()
      }
      return@withContext user
    }
}