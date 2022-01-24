package com.heavyforheavy.profiler.domain.usecase.viewers

import com.heavyforheavy.profiler.domain.repository.PermissionRepository
import com.heavyforheavy.profiler.domain.repository.RoleRepository
import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.ViewerResult
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetViewersByIdUseCase(
  private val userRelationRepository: UserRelationRepository,
  private val userRepository: UserRepository,
  private val permissionRepository: PermissionRepository,
  private val roleRepository: RoleRepository
) : UseCase<GetViewersByIdAction, GetViewersByIdResult> {

  override suspend fun invoke(action: GetViewersByIdAction): GetViewersByIdResult =
    withContext(Dispatchers.IO) {
      val currentUser = userRepository.getById(action.requesterId)
        ?: throw AuthException.InvalidToken()
      val permissions = permissionRepository.getUrlPermissions(ProfilerRoute.VIEWERS_BY_ID.url)
      val rolePermissions = roleRepository.getPermissions(currentUser.role)
      val viewers = if (rolePermissions.containsAll(permissions)) {
        userRelationRepository.getViewers(action.id, action.search, action.offset, action.limit)
      } else {
        throw RequestException.PermissionDenied()
      }
      GetViewersByIdResult(viewers)
    }
}

data class GetViewersByIdAction(
  val id: Int,
  val requesterId: Int,
  val search: String? = null,
  val offset: Int? = null,
  val limit: Int? = null
) : Action

data class GetViewersByIdResult(val viewers: List<ViewerResult>) : Result