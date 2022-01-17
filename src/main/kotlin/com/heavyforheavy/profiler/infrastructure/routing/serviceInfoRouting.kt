package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.serviceinfo.*
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.model.ServiceInfo
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.ServiceInfoException
import com.heavyforheavy.profiler.routes.Param
import com.heavyforheavy.profiler.routes.Routes
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
    val deleteServiceInfoByIdUseCase: DeleteServiceInfoByIdUseCase = get()

    route(Routes.SERVICES_INFO) {
        call.respond(getAllServiceInfoUseCase.getServiceInfos().response())
    }

    route(Routes.SERVICE_INFO) {
        val serviceId = call.requireParameter(Param.SERVICE_ID).toInt()
        call.respond(
            getServiceInfoUseCase.getServiceInfo(serviceId)?.response() ?: throw ServiceInfoException.ServiceInfoNotFound()
        )
    }

    route(Routes.ADD_SERVICE_INFO) {
        val serviceInfo = call.receive<ServiceInfo>()
        call.getUserIdPrincipal()?.name?.let { email ->
            call.respond(addServiceInfoUseCase.addServiceInfo(email, serviceInfo))
        } ?: AuthException.InvalidToken()
    }

    route(Routes.UPDATE_SERVICE_INFO) {
        val serviceInfo = call.receive<ServiceInfo>()
        call.getUserIdPrincipal()?.name?.let { email ->
            call.respond(updateServiceInfoUseCase.updateServiceInfo(email, serviceInfo))
        } ?: AuthException.InvalidToken()
    }

    route(Routes.DELETE_SERVICE_INFO) {
        val serviceInfo = call.receive<ServiceInfo>()
        call.getUserIdPrincipal()?.name?.let { email ->
            call.respond(deleteServiceInfoUseCase.deleteServiceInfo(email, serviceInfo))
        } ?: AuthException.InvalidToken()
    }

    route(Routes.DELETE_SERVICE_INFO_BY_ID) {
        val serviceId = call.requireParameter(Param.SERVICE_ID).toInt()
        call.getUserIdPrincipal()?.name?.let { email ->
            call.respond(deleteServiceInfoByIdUseCase.deleteServiceInfo(email, serviceId))
        } ?: AuthException.InvalidToken()
    }
}