package com.heavyforheavy.profiler.domain.usecase.serviceinfo

import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository,
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
  private val permissionRepository: PermissionRepository
) : UseCase<UpdateServiceInfoAction, UpdateServiceInfoResult> {
  override suspend fun invoke(action: UpdateServiceInfoAction): UpdateServiceInfoResult =
    withContext(Dispatchers.IO) {
      val requiredPermissions =
        permissionRepository.getUrlPermissions(ProfilerRoute.UPDATE_SERVICE_INFO.url)
      val user = userRepository.getById(action.userId) ?: throw AuthException.InvalidEmail()
      val userPermissions = roleRepository.getPermissions(user.role)
      val serviceInfo = if (userPermissions.containsAll(requiredPermissions)) {
        serviceInfoRepository.update(action.serviceInfo)
      } else {
        throw RequestException.PermissionDenied()
      }
      UpdateServiceInfoResult(serviceInfo)
    }
}

data class UpdateServiceInfoAction(val userId: Int, val serviceInfo: ServiceInfo) : Action

data class UpdateServiceInfoResult(val serviceInfo: ServiceInfo) : Result