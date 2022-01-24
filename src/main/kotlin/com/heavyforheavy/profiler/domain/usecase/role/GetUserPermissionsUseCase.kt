package com.heavyforheavy.profiler.domain.usecase.role

import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Permission
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserPermissionsUseCase(
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository
) : UseCase<GetUserPermissionsAction, GetUserPermissionsResult> {

  override suspend fun invoke(action: GetUserPermissionsAction): GetUserPermissionsResult =
    withContext(Dispatchers.IO) {
      val role = userRepository.getByEmail(action.userEmail)?.role
        ?: throw AuthException.InvalidEmail()
      val permissionList = roleRepository.getPermissions(role)
      GetUserPermissionsResult(permissionList)
    }
}

data class GetUserPermissionsAction(val userEmail: String) : Action

data class GetUserPermissionsResult(val permissions: List<Permission>) : Result