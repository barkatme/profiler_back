package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.TokenManager
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Token
import com.heavyforheavy.profiler.infrastructure.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateUserPasswordUseCase(
  val userRepository: UserRepository,
  private val tokenManager: TokenManager
) : UseCase<UpdateUserPasswordAction, UpdateUserPasswordResult> {
  override suspend fun invoke(action: UpdateUserPasswordAction): UpdateUserPasswordResult =
    withContext(Dispatchers.IO) {
      val newToken = tokenManager.createToken(action.user)
      val updatedUser = userRepository.update(
        action.user.copy(
          passwordHash = action.newPassword,
          token = newToken.token
        )
      )
      UpdateUserPasswordResult(updatedUser, newToken)
    }
}

data class UpdateUserPasswordAction(val user: User, val newPassword: String) : Action

data class UpdateUserPasswordResult(val user: User, val token: Token) : Result
