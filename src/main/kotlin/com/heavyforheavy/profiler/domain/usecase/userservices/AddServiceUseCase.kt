package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.UserService
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AddServiceUseCase(
  private val serviceRepository: UserServiceRepository
) : UseCase<AddServiceAction, AddServiceResult> {

  override suspend fun invoke(action: AddServiceAction): AddServiceResult =
    withContext(Dispatchers.IO) {
      val addedService = serviceRepository.addService(
        action.userId,
        action.userService.id,
        action.userService.userLink ?: throw RequestException.MissedData("user's link")
      )
      AddServiceResult(addedService)
    }
}

data class AddServiceAction(val userId: Int, val userService: UserService) : Action

data class AddServiceResult(val addedService: UserService) : Result

