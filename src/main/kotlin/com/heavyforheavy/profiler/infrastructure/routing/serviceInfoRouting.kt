package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.serviceinfo.*
import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Routing.serviceInfoRouting() {

  val getServiceInfoUseCase: GetServiceInfoUseCase = get()
  val getAllServiceInfoUseCase: GetAllServiceInfoUseCase = get()
  val addServiceInfoUseCase: AddServiceInfoUseCase = get()
  val updateServiceInfoUseCase: UpdateServiceInfoUseCase = get()
  val deleteServiceInfoUseCase: DeleteServiceInfoUseCase = get()

  route(ProfilerRoute.SERVICES_INFO) {
    call.respond(getAllServiceInfoUseCase.invoke(GetAllServiceInfoAction).serviceInfos.response())
  }

  route(ProfilerRoute.SERVICE_INFO) {
    val serviceId = call.requireParameter(Param.SERVICE_ID).toInt()
    call.respond(
      getServiceInfoUseCase.invoke(GetServiceInfoAction(serviceId)).serviceInfo.response()
    )
  }

  route(ProfilerRoute.ADD_SERVICE_INFO) {
    val serviceInfo = call.receive<ServiceInfo>()
    val action = AddServiceAction(call.requireTokenData().id, serviceInfo)
    call.respond(addServiceInfoUseCase.invoke(action).serviceInfo.response())
  }

  route(ProfilerRoute.UPDATE_SERVICE_INFO) {
    val serviceInfo = call.receive<ServiceInfo>()
    call.respond(
      updateServiceInfoUseCase.invoke(
        UpdateServiceInfoAction(call.requireTokenData().id, serviceInfo)
      ).serviceInfo.response()
    )
  }

  route(ProfilerRoute.DELETE_SERVICE_INFO) {
    val serviceInfo = call.receive<ServiceInfo>()
    call.respond(
      deleteServiceInfoUseCase.invoke(
        DeleteServiceInfoAction(call.requireTokenData().id, serviceInfo.id)
      ).isDeleted.response()
    )
  }

  route(ProfilerRoute.DELETE_SERVICE_INFO_BY_ID) {
    val serviceId = call.requireParameter(Param.SERVICE_ID).toInt()
    call.respond(
      deleteServiceInfoUseCase.invoke(
        DeleteServiceInfoAction(call.requireTokenData().id, serviceId)
      ).isDeleted.response()
    )
  }
}