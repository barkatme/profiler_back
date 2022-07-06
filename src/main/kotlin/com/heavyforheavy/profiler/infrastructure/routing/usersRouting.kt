package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.user.*
import com.heavyforheavy.profiler.infrastructure.model.User
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.getTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Routing.usersRouting() {

  val getOtherUserUseCase: GetOtherUserUseCase = get()
  val getUserByTokenUseCase: GetUserUseCase = get()
  val updateUserUseCase: UpdateUserUseCase = get()

  route(ProfilerRoute.USER) {
    val result = getUserByTokenUseCase.invoke(GetUserAction(call.requireTokenData().id))
    call.respond(result.user.response())
  }

  route(ProfilerRoute.UPDATE_USER) {
    val updatedUser = call.receive<User>()
    val result = updateUserUseCase.invoke(UpdateUserAction(call.requireTokenData(), updatedUser))
    call.respond(result.updatedUser.response())
  }

  route(ProfilerRoute.USER_BY_ID) {
    val uid = call.requireParameter(Param.USER_ID).toInt()
    val result = getOtherUserUseCase.invoke(GetOtherUserAction(call.getTokenData()?.id, uid))
    call.respond(result.user.response())
  }
}