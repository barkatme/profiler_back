package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.TokenData
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateUserUseCase(private val userRepository: UserRepository) :
  UseCase<UpdateUserAction, UpdateUserResult> {

  override suspend fun invoke(action: UpdateUserAction): UpdateUserResult =
    withContext(Dispatchers.IO) {

      if (action.tokenData.id != action.user.id) {
        throw RequestException.PermissionDenied()
      }

      //protect from updating token via endpoint
      action.user.token = action.tokenData.token

      val updatedUser = userRepository.update(action.user)
      UpdateUserResult(updatedUser)
    }
}

data class UpdateUserAction(val tokenData: TokenData, val user: User) : Action

data class UpdateUserResult(val updatedUser: User) : Result