package com.heavyforheavy.profiler.domain.usecase.viewers

import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteViewersByIdUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository,
  private val permissionRepository: PermissionRepository,
  private val roleRepository: RoleRepository
) : UseCase<DeleteViewersByIdAction, DeleteViewersByIdResult> {

  override suspend fun invoke(action: DeleteViewersByIdAction): DeleteViewersByIdResult =
    withContext(Dispatchers.IO) {
      val currentUser = userRepository.getById(action.requesterId)
        ?: throw AuthException.InvalidToken()
      val permissions = permissionRepository.getUrlPermissions(ProfilerRoute.DELETE_VIEWERS.url)
      val rolePermissions = roleRepository.getPermissions(currentUser.role)
      val isDeleted = if (rolePermissions.containsAll(permissions)) {
        userRelationRepository.deleteViewer(action.viewerId, action.userId) != 0
      } else {
        throw RequestException.PermissionDenied()
      }
      DeleteViewersByIdResult(isDeleted)
    }
}

data class DeleteViewersByIdAction(
  val userId: Int,
  val viewerId: Int,
  val requesterId: Int
) : Action

data class DeleteViewersByIdResult(val isDeleted: Boolean) : Result