package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteSavedUserUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository
) {

  @Suppress("MemberVisibilityCanBePrivate")
  suspend fun deleteSavedUser(userId: Int, savedUserId: Int) = withContext(Dispatchers.IO) {
    userRelationRepository.deleteSavedUser(userId, savedUserId)
  }

  suspend fun deleteSavedUser(userEmail: String?, savedUserId: Int) = withContext(Dispatchers.IO) {
    val currentUser: User =
      userEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidToken()
    deleteSavedUser(currentUser.id, savedUserId)
  }
}