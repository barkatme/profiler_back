package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserByEmailUseCase(private val userRepository: UserRepository) {
  suspend fun getUser(email: String): User? = withContext(Dispatchers.IO) {
    userRepository.getByEmail(email)
  }
}