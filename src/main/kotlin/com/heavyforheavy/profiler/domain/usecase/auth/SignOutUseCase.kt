package com.heavyforheavy.profiler.domain.usecase.auth

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SignOutUseCase(private val userRepository: UserRepository) {

    suspend fun signOut(
        email: String,
        tokenGenerator: (User) -> String
    ): String =
        withContext(Dispatchers.IO) {
            val user = userRepository.getByEmail(email) ?: throw AuthException.InvalidAuth()
            val newToken = tokenGenerator(user)
            user.token = newToken
            userRepository.update(user)
            newToken
        }
}