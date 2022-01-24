package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteSavedUserUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository
) : UseCase<DeleteSavedUserAction, DeleteSavedUserResult> {

  override suspend fun invoke(action: DeleteSavedUserAction): DeleteSavedUserResult =
    withContext(Dispatchers.IO) {
      val isDeleted = when (action) {

        is DeleteSavedUserAction.ById -> {
          userRelationRepository.deleteSavedUser(action.userId, action.savedUserId) != 0
        }

        is DeleteSavedUserAction.ByEmail -> {
          val currentUser: User = action.userEmail?.let { userRepository.getByEmail(it) }
            ?: throw AuthException.InvalidToken()
          userRelationRepository.deleteSavedUser(currentUser.id, action.savedUserId) != 0
        }
      }

      DeleteSavedUserResult(isDeleted)
    }
}

sealed class DeleteSavedUserAction(open val savedUserId: Int) : Action {
  data class ById(
    val userId: Int = -1,
    override val savedUserId: Int
  ) : DeleteSavedUserAction(savedUserId)

  data class ByEmail(
    val userEmail: String? = null,
    override val savedUserId: Int
  ) : DeleteSavedUserAction(savedUserId)
}

data class DeleteSavedUserResult(val isDeleted: Boolean) : Result