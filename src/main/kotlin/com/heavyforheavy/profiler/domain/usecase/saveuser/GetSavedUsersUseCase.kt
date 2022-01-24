package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.SavedUserResult
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSavedUsersUseCase(
  private val userRelationRepository: UserRelationRepository
) : UseCase<GetSavedUsersAction, GetSavedUsersResult> {

  override suspend fun invoke(action: GetSavedUsersAction): GetSavedUsersResult =
    withContext(Dispatchers.IO) {

      //TODO: check if requesterId user have permission to see the saved users if they aren't equal
      // for admin users
      if (action.userId != action.requesterId) {
        throw RequestException.PermissionDenied(message = "you can see only your saved users")
      }

      val savedUsersResult: List<SavedUserResult> =
        userRelationRepository.getSavedUsers(
          action.userId,
          action.search,
          action.offset,
          action.limit
        )
      GetSavedUsersResult(savedUsersResult)
    }
}

data class GetSavedUsersAction(
  val userId: Int,
  val search: String? = null,
  val offset: Int? = null,
  val limit: Int? = null,
  val requesterId: Int
) : Action

data class GetSavedUsersResult(val savedUsers: List<SavedUserResult>) : Result