package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateUserUseCase(private val userRepository: UserRepository) {
  suspend fun updateUser(currentUser: User?, user: User): User = withContext(Dispatchers.IO) {
    if (currentUser?.id != user.id) {
      throw RequestException.PermissionDenied()
    }
    //protect from updating token via endpoint
    user.token = null
    userRepository.update(user)
  }
}