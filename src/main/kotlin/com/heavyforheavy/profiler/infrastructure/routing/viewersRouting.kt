package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.viewers.DeleteViewersByIdUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.DeleteViewersUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.GetViewersByIdUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.GetViewersUseCase
import com.heavyforheavy.profiler.infrastructure.routing.routes.getParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.model.response
import com.heavyforheavy.profiler.routes.Param
import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get


fun Routing.viewedUsersRouting() {

    val getViewersUseCase: GetViewersUseCase = get()
    val getViewersByIdUseCase: GetViewersByIdUseCase = get()
    val deleteViewersByIdUseCase: DeleteViewersByIdUseCase = get()
    val deleteViewersUseCase: DeleteViewersUseCase = get()

    route(Routes.VIEWERS) {
        call.respond(
            getViewersUseCase.getViewedUsers(
                call.getUserIdPrincipal()?.name,
                call.getParameter(Param.SEARCH),
                call.getParameter(Param.OFFSET)?.toIntOrNull(),
                call.getParameter(Param.LIMIT)?.toIntOrNull()
            ).response()
        )
    }

    route(Routes.VIEWERS_BY_ID) {
        val userId = call.requireParameter(Param.USER_ID).toInt()
        call.respond(
            getViewersByIdUseCase.getViewers(
                userId,
                call.getUserIdPrincipal()?.name,
                call.getParameter(Param.SEARCH),
                call.getParameter(Param.OFFSET)?.toIntOrNull(),
                call.getParameter(Param.LIMIT)?.toIntOrNull()
            ).response()
        )
    }

    route(Routes.DELETE_VIEWERS) {
        val userId = call.requireParameter(Param.USER_ID).toInt()
        call.respond(deleteViewersUseCase.deleteViewers(userId, call.getUserIdPrincipal()?.name))
    }

    route(Routes.DELETE_VIEWERS_BY_ID) {
        val userId = call.requireParameter(Param.USER_ID).toInt()
        val viewerId = call.requireParameter(Param.VIEWER_ID).toInt()
        call.respond(
            deleteViewersByIdUseCase.deleteViewers(
                userId,
                viewerId,
                call.getUserIdPrincipal()?.name
            ).response()
        )
    }
}