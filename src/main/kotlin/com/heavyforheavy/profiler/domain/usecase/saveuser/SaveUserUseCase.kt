package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.DatabaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveUserUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository
) {

  @Suppress("MemberVisibilityCanBePrivate")
  suspend fun saveUser(userId: Int, userToSaveId: Int): User = withContext(Dispatchers.IO) {
    userRelationRepository.saveUser(userId, userToSaveId)
    userRepository.getById(userToSaveId) ?: throw DatabaseException.OperationFailed()
  }

  suspend fun saveUser(userEmail: String?, userToSaveId: Int): User = withContext(Dispatchers.IO) {
    val user =
      userEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidEmail()
    saveUser(user.id, userToSaveId)
  }
}