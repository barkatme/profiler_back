package com.heavyforheavy.profiler.domain.usecase.user

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.exception.DatabaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserByEmailUseCase(private val userRepository: UserRepository) :
  UseCase<GetUserByEmailAction, GetUserByEmailResult> {

  override suspend fun invoke(action: GetUserByEmailAction): GetUserByEmailResult =
    withContext(Dispatchers.IO) {
      GetUserByEmailResult(
        userRepository.getByEmail(action.email) ?: throw DatabaseException.OperationFailed()
      )
    }
}

data class GetUserByEmailAction(val email: String) : Action

data class GetUserByEmailResult(val user: User) : Result