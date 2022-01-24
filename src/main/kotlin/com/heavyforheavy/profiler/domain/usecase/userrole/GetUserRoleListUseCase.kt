package com.heavyforheavy.profiler.domain.usecase.userrole

import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.Role
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserRoleListUseCase(private val roleRepository: RoleRepository) :
  UseCase<GetUserRoleListAction, GetUserRoleListResult> {

  override suspend fun invoke(action: GetUserRoleListAction): GetUserRoleListResult =
    withContext(Dispatchers.IO) {
      GetUserRoleListResult(roleRepository.getAll())
    }
}

object GetUserRoleListAction : Action

data class GetUserRoleListResult(
  val roles: List<Role>
) : Result