package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.userservices.*
import com.heavyforheavy.profiler.infrastructure.model.UserService
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Routing.userServicesRouting() {

  val addServiceUseCase: AddServiceUseCase = get()
  val deleteServiceUseCase: DeleteServiceUseCase = get()
  val updateServiceUseCase: UpdateServiceUseCase = get()
  val getServicesUseCase: GetServicesUseCase = get()
  val getUserAndServicesUseCase: GetUserAndServicesUseCase = get()

  route(ProfilerRoute.USER_AND_SERVICES) {
    val result = getUserAndServicesUseCase.invoke(
      GetUserAndServicesAction(call.requireTokenData().id)
    )
    call.respond(result.userAndServices.response())
  }

  route(ProfilerRoute.USER_AND_SERVICES_BY_ID) {
    val result = getUserAndServicesUseCase.invoke(
      GetUserAndServicesAction(call.requireParameter(Param.USER_ID).toInt())
    )
    call.respond(result.userAndServices.response())
  }

  route(ProfilerRoute.USER_SERVICES) {
    val result = getServicesUseCase.invoke(GetServicesAction(call.requireTokenData().id))
    call.respond(result.services.response())
  }

  route(ProfilerRoute.USER_SERVICES_BY_ID) {
    val result = getServicesUseCase.invoke(
      GetServicesAction(call.requireParameter(Param.USER_ID).toInt())
    )
    call.respond(result.services.response())
  }

  route(ProfilerRoute.ADD_SERVICE) {
    val service = call.receive<UserService>()
    val result = addServiceUseCase.invoke(AddServiceAction(call.requireTokenData().id, service))
    call.respond(result.addedService.response())
  }

  route(ProfilerRoute.UPDATE_SERVICE) {
    val service = call.receive<UserService>()
    val result =
      updateServiceUseCase.invoke(UpdateServiceAction(call.requireTokenData().id, service))
    call.respond(result.userService.response())
  }

  route(ProfilerRoute.DELETE_SERVICE) {
    call.respond(
      deleteServiceUseCase
        .invoke(DeleteServiceAction(call.requireParameter(Param.SERVICE_ID).toInt()))
        .isDeleted
        .response()
    )
  }
}