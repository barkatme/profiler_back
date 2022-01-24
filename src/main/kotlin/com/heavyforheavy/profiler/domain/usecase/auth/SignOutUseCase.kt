package com.heavyforheavy.profiler.domain.usecase.auth

import com.heavyforheavy.profiler.domain.TokenManager
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Token
import com.heavyforheavy.profiler.infrastructure.model.TokenData
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SignOutUseCase(
  private val userRepository: UserRepository,
  private val tokenManager: TokenManager
) : UseCase<SignOutAction, SignOutResult> {

  override suspend fun invoke(action: SignOutAction): SignOutResult = withContext(Dispatchers.IO) {
    val user = userRepository.getById(action.tokenData.id)
      ?: throw AuthException.InvalidAuth()
    val newToken = tokenManager.createToken(user)
    user.token = newToken.token
    userRepository.update(user)
    SignOutResult(newToken)
  }
}

data class SignOutAction(val tokenData: TokenData) : Action

data class SignOutResult(val token: Token) : Result