package com.heavyforheavy.profiler.domain.usecase.serviceinfo

import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository,
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
  private val permissionRepository: PermissionRepository
) : UseCase<DeleteServiceInfoAction, DeleteServiceInfoResult> {

  override suspend fun invoke(action: DeleteServiceInfoAction): DeleteServiceInfoResult =
    withContext(Dispatchers.IO) {
      val requiredPermissions =
        permissionRepository.getUrlPermissions(ProfilerRoute.DELETE_SERVICE_INFO.url)
      val user = userRepository.getById(action.userId) ?: throw AuthException.InvalidEmail()
      val userPermissions = roleRepository.getPermissions(user.role)

      val isDeleted = if (userPermissions.containsAll(requiredPermissions)) {
        serviceInfoRepository.delete(action.serviceInfoId) != 0
      } else {
        throw RequestException.PermissionDenied()
      }

      DeleteServiceInfoResult(isDeleted)
    }
}

data class DeleteServiceInfoAction(val userId: Int, val serviceInfoId: Int) : Action

data class DeleteServiceInfoResult(val isDeleted: Boolean) : Result