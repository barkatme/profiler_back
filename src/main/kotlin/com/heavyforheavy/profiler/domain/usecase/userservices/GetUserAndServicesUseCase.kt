package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.UserAndServices
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserAndServicesUseCase(
  private val serviceRepository: UserServiceRepository,
  private val userRepository: UserRepository
) : UseCase<GetUserAndServicesAction, GetUserAndServicesResult> {

  override suspend fun invoke(action: GetUserAndServicesAction): GetUserAndServicesResult =
    withContext(Dispatchers.IO) {
      val user = userRepository.getById(action.userId)
        ?: throw RequestException.OperationFailed(message = "user with such id wasn't found")
      val userAndServices = getServices(user)
      GetUserAndServicesResult(userAndServices)
    }

  private suspend fun getServices(user: User): UserAndServices = withContext(Dispatchers.IO) {
    val services = serviceRepository.getByUserId(user.id)
    UserAndServices(user, services)
  }
}

data class GetUserAndServicesAction(val userId: Int) : Action

data class GetUserAndServicesResult(val userAndServices: UserAndServices) : Result
