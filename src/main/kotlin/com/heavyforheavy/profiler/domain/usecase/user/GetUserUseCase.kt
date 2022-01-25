package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserUseCase(
  private val userRepository: UserRepository
) : UseCase<GetUserAction, GetUserResult> {
  override suspend fun invoke(action: GetUserAction): GetUserResult = withContext(Dispatchers.IO) {
    GetUserResult(userRepository.getById(action.id) ?: throw RequestException.OperationFailed())
  }
}

data class GetUserAction(val id: Int) : Action

data class GetUserResult(val user: User) : Result