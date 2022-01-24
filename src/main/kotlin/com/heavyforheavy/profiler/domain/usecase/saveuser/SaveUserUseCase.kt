package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.DatabaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveUserUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository
) : UseCase<SaveUserAction, SaveUserResult> {

  override suspend fun invoke(action: SaveUserAction): SaveUserResult =
    withContext(Dispatchers.IO) {

      val user: User = when (action) {
        is SaveUserAction.ByID -> {
          userRelationRepository.saveUser(action.userId, action.userToSaveId)
          userRepository.getById(action.userToSaveId) ?: throw DatabaseException.OperationFailed()
        }

        is SaveUserAction.ByEmail -> {
          val user = action.userEmail?.let { userRepository.getByEmail(it) }
            ?: throw AuthException.InvalidEmail()
          userRelationRepository.saveUser(user.id, action.userToSaveId)
          userRepository.getById(action.userToSaveId) ?: throw DatabaseException.OperationFailed()
        }
      }
      SaveUserResult(user)
    }
}

sealed class SaveUserAction(open val userToSaveId: Int) : Action {

  data class ByID(
    val userId: Int,
    override val userToSaveId: Int
  ) : SaveUserAction(userToSaveId)

  data class ByEmail(
    val userEmail: String?,
    override val userToSaveId: Int
  ) : SaveUserAction(userToSaveId)
}

data class SaveUserResult(val savedUser: User) : Result