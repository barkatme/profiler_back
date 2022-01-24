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

class AddServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository,
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
  private val permissionRepository: PermissionRepository
) : UseCase<AddServiceAction, AddServiceResult> {

  override suspend fun invoke(action: AddServiceAction): AddServiceResult =
    withContext(Dispatchers.IO) {

      val requiredPermissions =
        permissionRepository.getUrlPermissions(ProfilerRoute.ADD_SERVICE_INFO.url)

      val user = userRepository.getById(action.creatorId)
        ?: throw AuthException.InvalidEmail()
      val userPermissions = roleRepository.getPermissions(user.role)

      val serviceInfo = if (userPermissions.containsAll(requiredPermissions)) {
        serviceInfoRepository.insert(action.serviceInfo)
      } else {
        throw RequestException.PermissionDenied()
      }
      AddServiceResult(serviceInfo)
    }
}

data class AddServiceAction(val creatorId: Int, val serviceInfo: ServiceInfo) : Action

data class AddServiceResult(val serviceInfo: ServiceInfo) : Result