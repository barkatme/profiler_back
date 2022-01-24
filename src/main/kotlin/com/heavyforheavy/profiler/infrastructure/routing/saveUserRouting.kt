package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.saveuser.*
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.getParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Routing.saveUserRoting() {

  val getSavedUsersUseCase: GetSavedUsersUseCase = get()
  val saveUserUseCase: SaveUserUseCase = get()
  val deleteSavedUserUseCase: DeleteSavedUserUseCase = get()

  route(ProfilerRoute.SAVE_USER) {
    call.respond(
      saveUserUseCase.invoke(
        SaveUserAction.ByEmail(
          call.requireTokenData().email,
          call.requireParameter(Param.USER_ID).toInt()
        )
      ).savedUser.response()
    )
  }

  route(ProfilerRoute.SAVED_USERS) {
    call.respond(
      getSavedUsersUseCase.invoke(
        GetSavedUsersAction(
          call.requireTokenData().id,
          call.getParameter(Param.SEARCH),
          call.getParameter(Param.OFFSET)?.toIntOrNull(),
          call.getParameter(Param.LIMIT)?.toIntOrNull(),
          call.requireTokenData().id
        )
      ).savedUsers.response()
    )
  }

  route(ProfilerRoute.SAVED_USERS_BY_ID) {
    call.respond(
      getSavedUsersUseCase.invoke(
        GetSavedUsersAction(
          call.requireParameter(Param.USER_ID).toInt(),
          call.getParameter(Param.SEARCH),
          call.getParameter(Param.OFFSET)?.toIntOrNull(),
          call.getParameter(Param.LIMIT)?.toIntOrNull(),
          call.requireTokenData().id
        )
      ).savedUsers.response()
    )
  }

  route(ProfilerRoute.DELETE_SAVED_USER) {
    call.respond(
      deleteSavedUserUseCase.invoke(
        DeleteSavedUserAction.ByEmail(
          call.requireTokenData().email,
          call.requireParameter(Param.USER_ID).toInt()
        )
      ).isDeleted.response()
    )
  }
}