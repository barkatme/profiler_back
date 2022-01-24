package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOtherUserUseCase(
  private val userRepository: UserRepository,
  private val userRelationRepository: UserRelationRepository
) : UseCase<GetOtherUserAction, GetOtherUserResult> {

  override suspend fun invoke(action: GetOtherUserAction): GetOtherUserResult =
    withContext(Dispatchers.IO) {
      val currentUser = action.currentUserId?.let { userRepository.getById(it) }
      val user = userRepository.getById(action.userId)
      if (currentUser != null && user != null && currentUser.id != user.id) {
        userRelationRepository.viewUser(currentUser.id, user.id)
      }
      GetOtherUserResult(user ?: throw AuthException.InvalidUserId())
    }
}

data class GetOtherUserAction(val currentUserId: Int?, val userId: Int) : Action

data class GetOtherUserResult(val user: User) : Result