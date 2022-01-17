package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveUserUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository
) {

  @Suppress("MemberVisibilityCanBePrivate")
  suspend fun saveUser(userId: Int, userToSaveId: Int) = withContext(Dispatchers.IO) {
    userRelationRepository.saveUser(userId, userToSaveId)
  }

  suspend fun saveUser(userEmail: String?, userToSaveId: Int) = withContext(Dispatchers.IO) {
    val user =
      userEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidEmail()
    saveUser(user.id, userToSaveId)
  }
}