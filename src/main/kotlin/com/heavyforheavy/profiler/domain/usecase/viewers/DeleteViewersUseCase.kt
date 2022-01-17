package com.heavyforheavy.profiler.domain.usecase.viewers

import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.RequestException
import com.heavyforheavy.profiler.routes.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteViewersUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository,
  private val permissionRepository: PermissionRepository,
  private val roleRepository: RoleRepository
) {
  suspend fun deleteViewers(userId: Int, requesterEmail: String?): Boolean = withContext(
    Dispatchers
      .IO
  ) {
    val currentUser =
      requesterEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidToken()
    val permissions = permissionRepository.getUrlPermissions(Routes.DELETE_VIEWERS_BY_ID.url)
    val rolePermissions = roleRepository.getPermissions(currentUser.role)
    if (rolePermissions.containsAll(permissions)) {
      userRelationRepository.deleteViewers(userId) != 0
    } else {
      throw RequestException.PermissionDenied()
    }
  }
}