package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.viewers.*
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.getParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


fun Routing.viewedUsersRouting() {

  val getViewersUseCase: GetViewersUseCase = get()
  val getViewersByIdUseCase: GetViewersByIdUseCase = get()
  val deleteViewersByIdUseCase: DeleteViewersByIdUseCase = get()
  val deleteViewersUseCase: DeleteViewersUseCase = get()

  route(ProfilerRoute.VIEWERS) {
    val result = getViewersUseCase.invoke(
      GetViewersAction(
        call.requireTokenData().id,
        call.getParameter(Param.SEARCH),
        call.getParameter(Param.OFFSET)?.toIntOrNull(),
        call.getParameter(Param.LIMIT)?.toIntOrNull()
      )
    )
    call.respond(result.viewers.response())
  }

  route(ProfilerRoute.VIEWERS_BY_ID) {
    val userId = call.requireParameter(Param.USER_ID).toInt()
    val result = getViewersByIdUseCase.invoke(
      GetViewersByIdAction(
        id = userId,
        requesterId = call.requireTokenData().id,
        search = call.getParameter(Param.SEARCH),
        offset = call.getParameter(Param.OFFSET)?.toIntOrNull(),
        limit = call.getParameter(Param.LIMIT)?.toIntOrNull()
      )
    )
    call.respond(result.viewers.response())
  }

  route(ProfilerRoute.DELETE_VIEWERS) {
    val userId = call.requireParameter(Param.USER_ID).toInt()
    val result = deleteViewersUseCase.invoke(
      DeleteViewersAction(userId, call.requireTokenData().id)
    )
    call.respond(result.isDeleted.response())
  }

  route(ProfilerRoute.DELETE_VIEWERS_BY_ID) {
    val result = deleteViewersByIdUseCase.invoke(
      DeleteViewersByIdAction(
        userId = call.requireParameter(Param.USER_ID).toInt(),
        viewerId = call.requireParameter(Param.VIEWER_ID).toInt(),
        requesterId = call.requireTokenData().id
      )
    )
    call.respond(result.isDeleted.response())
  }
}