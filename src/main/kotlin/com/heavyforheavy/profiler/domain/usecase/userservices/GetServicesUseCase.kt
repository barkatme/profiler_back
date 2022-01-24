package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetServicesUseCase(
  private val serviceRepository: UserServiceRepository
) : UseCase<GetServicesAction, GetServicesResult> {

  override suspend fun invoke(action: GetServicesAction): GetServicesResult =
    withContext(Dispatchers.IO) {
      GetServicesResult(serviceRepository.getByUserId(action.userId))
    }
}

data class GetServicesAction(val userId: Int) : Action

data class GetServicesResult(val services: List<UserService>) : Result
