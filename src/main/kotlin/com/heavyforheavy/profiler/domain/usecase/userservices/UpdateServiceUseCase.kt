package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.UserService
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateServiceUseCase(
  private val serviceRepository: UserServiceRepository,
  private val userRepository: UserRepository
) : UseCase<UpdateServiceAction, UpdateServiceResult> {

  override suspend fun invoke(action: UpdateServiceAction): UpdateServiceResult =
    withContext(Dispatchers.IO) {
      val user = userRepository.getById(action.userId)
        ?: throw AuthException.InvalidEmail()
      if (user.id != action.userService.userId) {
        throw RequestException.OperationFailed(
          message = "userId of service doesn't equals to user's id (foreign key violation)"
        )
      }
      val updatedService = serviceRepository.updateServiceLink(action.userService)

      UpdateServiceResult(updatedService)
    }
}

data class UpdateServiceAction(val userId: Int, val userService: UserService) : Action

data class UpdateServiceResult(val userService: UserService) : Result