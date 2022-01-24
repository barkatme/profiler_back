package com.heavyforheavy.profiler.domain.usecase.auth

import com.heavyforheavy.profiler.domain.TokenManager
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Token
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.exceptions.ExposedSQLException

class SignUpUseCase(
  private val userRepository: UserRepository,
  private val tokenManager: TokenManager
) : UseCase<SignUpAction, SignUpResult> {

  override suspend fun invoke(action: SignUpAction): SignUpResult =
    withContext(Dispatchers.IO) {
      val user = User(
        email = action.credentials.email,
        passwordHash = action.credentials.password
      )
      val newToken = tokenManager.createToken(user)
      user.token = newToken.token

      try {
        userRepository.insert(user)
      } catch (e: ExposedSQLException) {
        //TODO: throw repository exceptions from within the repository
        throw AuthException.EmailAlreadyExists()
      }

      return@withContext SignUpResult(newToken)
    }
}

data class SignUpAction(val credentials: com.heavyforheavy.profiler.infrastructure.model.Credentials) :
  Action

data class SignUpResult(val token: Token) : Result