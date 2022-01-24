package com.heavyforheavy.profiler.domain.usecase.auth


import com.heavyforheavy.profiler.domain.TokenManager
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Token
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUseCase(
  private val userRepository: UserRepository,
  private val tokenManager: TokenManager
) : UseCase<SignInAction, SignInResult> {

  override suspend fun invoke(action: SignInAction): SignInResult = withContext(Dispatchers.IO) {
    val user = userRepository.getByEmail(action.credentials.email)
    if (user == null) {
      throw AuthException.InvalidEmail()
    } else if (action.credentials.password != user.passwordHash) {
      throw AuthException.InvalidPassword()
    }

    val newToken = user.token?.takeIf { tokenManager.checkToken(Token(it)) }?.let { Token(it) }
      ?: run {
        tokenManager.createToken(user)
      }

    user.token = newToken.token
    userRepository.update(user)

    SignInResult(newToken)
  }
}


data class SignInAction(
  val credentials: com.heavyforheavy.profiler.infrastructure.model.Credentials
) : Action

data class SignInResult(val token: Token) : Result