package com.heavyforheavy.profiler.domain.usecase.viewers

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.ViewerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetViewersUseCase(
  private val userRelationRepository: UserRelationRepository
) : UseCase<GetViewersAction, GetViewersResult> {

  override suspend fun invoke(action: GetViewersAction): GetViewersResult =
    withContext(Dispatchers.IO) {
      val viewers = userRelationRepository.getViewers(
        userId = action.userId,
        search = action.search,
        offset = action.offset,
        limit = action.limit
      )
      GetViewersResult(viewers)
    }
}

data class GetViewersAction(
  val userId: Int,
  val search: String? = null,
  val offset: Int? = null,
  val limit: Int? = null
) : Action

data class GetViewersResult(val viewers: List<ViewerResult>) : Result