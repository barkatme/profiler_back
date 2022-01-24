package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteServiceUseCase(
  private val serviceRepository: UserServiceRepository
) : UseCase<DeleteServiceAction, DeleteServiceResult> {

  override suspend fun invoke(action: DeleteServiceAction): DeleteServiceResult =
    withContext(Dispatchers.IO) {
      DeleteServiceResult(serviceRepository.deleteUserService(action.serviceId) != 0)
    }
}

data class DeleteServiceAction(val serviceId: Int) : Action

data class DeleteServiceResult(val isDeleted: Boolean) : Result