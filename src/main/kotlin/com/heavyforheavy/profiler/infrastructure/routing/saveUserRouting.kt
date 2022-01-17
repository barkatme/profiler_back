package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.saveuser.DeleteSavedUserUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.GetSavedUsersUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.SaveUserUseCase
import com.heavyforheavy.profiler.infrastructure.routing.routes.getParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.routes.Param
import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Routing.saveUserRoting() {

  val getSavedUsersUseCase: GetSavedUsersUseCase = get()
  val saveUserUseCase: SaveUserUseCase = get()
  val deleteSavedUserUseCase: DeleteSavedUserUseCase = get()

  route(Routes.SAVE_USER) {
    call.respond(
      saveUserUseCase.saveUser(
        call.getUserIdPrincipal()?.name,
        call.requireParameter(Param.USER_ID).toInt()
      )
    )
  }

  route(Routes.SAVED_USERS) {
    call.respond(
      getSavedUsersUseCase.getSavedUsers(
        call.getUserIdPrincipal()?.name,
        call.getParameter(Param.SEARCH),
        call.getParameter(Param.OFFSET)?.toIntOrNull(),
        call.getParameter(Param.LIMIT)?.toIntOrNull()
      ).response()
    )
  }

  route(Routes.SAVED_USERS_BY_ID) {
    call.respond(
      getSavedUsersUseCase.getSavedUsers(
        call.requireParameter(Param.USER_ID).toInt(),
        call.getParameter(Param.SEARCH),
        call.getParameter(Param.OFFSET)?.toIntOrNull(),
        call.getParameter(Param.LIMIT)?.toIntOrNull()
      ).response()
    )
  }

  route(Routes.DELETE_SAVED_USER) {
    call.respond(
      deleteSavedUserUseCase.deleteSavedUser(
        call.getUserIdPrincipal()?.name,
        call.requireParameter(Param.USER_ID).toInt()
      )
    )
  }
}