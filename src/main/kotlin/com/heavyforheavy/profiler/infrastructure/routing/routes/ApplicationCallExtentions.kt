package com.heavyforheavy.profiler.infrastructure.routing.routes

import com.heavyforheavy.profiler.model.exception.RequestException
import com.heavyforheavy.profiler.routes.Param
import io.ktor.application.*
import io.ktor.auth.*

fun ApplicationCall.getUserIdPrincipal(): UserIdPrincipal? {
  return principal()
}

fun ApplicationCall.getToken(): String? {
  return request.headers["Authorization"]?.split(" ")?.takeIf { it.size > 1 }?.run { this[1] }
}

fun ApplicationCall.getParameter(param: Param): String? = parameters[param.key]

fun ApplicationCall.requireParameter(param: Param): String =
  getParameter(param) ?: throw RequestException.ParameterNotFound(param)
