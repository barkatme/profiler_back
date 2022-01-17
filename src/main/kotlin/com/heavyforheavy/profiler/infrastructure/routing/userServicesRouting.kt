package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.userservices.*
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.model.UserService
import com.heavyforheavy.profiler.model.response
import com.heavyforheavy.profiler.routes.Param
import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Routing.userServicesRouting() {

  val addServiceUseCase: AddServiceUseCase = get()
  val deleteServiceUseCase: DeleteServiceUseCase = get()
  val updateServiceUseCase: UpdateServiceUseCase = get()
  val getServicesUseCase: GetServicesUseCase = get()
  val getUserAndServicesUseCase: GetUserAndServicesUseCase = get()

  route(Routes.USER_AND_SERVICES) {
    call.respond(getUserAndServicesUseCase.getServices(call.getUserIdPrincipal()?.name).response())
  }

  route(Routes.USER_AND_SERVICES_BY_ID) {
    call.respond(
      getUserAndServicesUseCase.getServices(call.requireParameter(Param.USER_ID).toInt()).response()
    )
  }

  route(Routes.USER_SERVICES) {
    call.respond(getServicesUseCase.getServices(call.getUserIdPrincipal()?.name).response())
  }

  route(Routes.USER_SERVICES_BY_ID) {
    call.respond(
      getServicesUseCase.getServices(call.requireParameter(Param.USER_ID).toInt()).response()
    )
  }

  route(Routes.ADD_SERVICE) {
    val service = call.receive<UserService>()
    call.respond(addServiceUseCase.addService(call.getUserIdPrincipal()?.name, service).response())
  }

  route(Routes.UPDATE_SERVICE) {
    val service = call.receive<UserService>()
    call.respond(
      updateServiceUseCase.updateService(call.getUserIdPrincipal()?.name, service).response()
    )
  }

  route(Routes.DELETE_SERVICE) {
    call.respond(
      deleteServiceUseCase.deleteService(
        call.requireParameter(Param.SERVICE_ID).toInt()
      ).response()
    )
  }
}