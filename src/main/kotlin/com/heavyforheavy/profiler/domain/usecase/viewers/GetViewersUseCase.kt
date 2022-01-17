package com.heavyforheavy.profiler.domain.usecase.viewers

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetViewersUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository
) {
  suspend fun getViewedUsers(
    email: String?,
    search: String? = null,
    offset: Int? = null,
    limit: Int? = null
  ): List<UserRelationRepository.ViewerResult> = withContext(Dispatchers.IO) {
    val currentUser = email?.let { userRepository.getByEmail(it) }
      ?: throw AuthException.InvalidToken()
    userRelationRepository.getViewers(currentUser.id, search, offset, limit)
  }
}