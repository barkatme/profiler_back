package com.heavyforheavy.profiler.domain.usecase.role

import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Permission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRolePermissionsUseCase(
  private val roleRepository: RoleRepository
) : UseCase<GetRolePermissionsAction, GetRolePermissionsResult> {

  override suspend fun invoke(action: GetRolePermissionsAction): GetRolePermissionsResult =
    withContext(Dispatchers.IO) {
      val permissions = roleRepository.getPermissions(action.roleId)
      GetRolePermissionsResult(permissions)
    }
}

data class GetRolePermissionsAction(val roleId: Int) : Action

data class GetRolePermissionsResult(val permissions: List<Permission>) : Result