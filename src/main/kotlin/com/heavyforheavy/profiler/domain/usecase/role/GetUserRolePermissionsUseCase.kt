package com.heavyforheavy.profiler.domain.usecase.role

import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.Permission
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserRolePermissionsUseCase(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {
    suspend fun getUserPermissions(userEmail: String): List<Permission> = withContext(Dispatchers.IO) {
        val role = userRepository.getByEmail(userEmail)?.role ?: throw AuthException.InvalidEmail()
        roleRepository.getPermissions(role)
    }
}