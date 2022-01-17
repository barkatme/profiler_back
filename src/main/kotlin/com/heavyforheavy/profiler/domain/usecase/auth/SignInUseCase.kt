package com.heavyforheavy.profiler.domain.usecase.auth


import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUseCase(private val userRepository: UserRepository) {

  suspend fun signIn(
    email: String,
    password: String,
    tokenChecker: (String) -> Boolean,
    tokenGenerator: (User) -> String
  ): String =
    withContext(Dispatchers.IO) {
      val user = userRepository.getByEmail(email)
      if (user == null) {
        throw AuthException.InvalidAuth()
      } else if (password != user.passwordHash) {
        throw AuthException.InvalidPassword()
      }
      return@withContext user.token?.takeIf { tokenChecker(it) } ?: run {
        val newToken = tokenGenerator(user)
        user.token = newToken
        userRepository.update(user)
        newToken
      }
    }
}