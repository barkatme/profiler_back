package com.heavyforheavy.profiler.domain.usecase.serviceinfo

import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.ServiceInfo
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.RequestException
import com.heavyforheavy.profiler.routes.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository,
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
  private val permissionRepository: PermissionRepository
) {
  suspend fun updateServiceInfo(updaterEmail: String, serviceInfo: ServiceInfo): ServiceInfo =
    withContext(Dispatchers.IO) {
      val requiredPermissions =
        permissionRepository.getUrlPermissions(Routes.UPDATE_SERVICE_INFO.url)
      val user = userRepository.getByEmail(updaterEmail) ?: throw AuthException.InvalidEmail()
      val userPermissions = roleRepository.getPermissions(user.role)
      if (userPermissions.containsAll(requiredPermissions)) {
        serviceInfoRepository.update(serviceInfo)
      } else {
        throw RequestException.PermissionDenied()
      }
    }
}