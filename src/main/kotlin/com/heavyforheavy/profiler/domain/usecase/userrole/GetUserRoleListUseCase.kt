package com.heavyforheavy.profiler.domain.usecase.userrole

import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.model.Role
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserRoleListUseCase(private val roleRepository: RoleRepository) {

    suspend fun getUserRoles(): List<Role> = withContext(Dispatchers.IO) {
        roleRepository.getAll()
    }
}