package com.heavyforheavy.profiler.domain.usecase.role

import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.model.Permission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRolePermissionsUseCase(
    private val roleRepository: RoleRepository
) {
    suspend fun getUserPermissions(roleId: Int): List<Permission> = withContext(Dispatchers.IO) {
        roleRepository.getPermissions(roleId)
    }
}