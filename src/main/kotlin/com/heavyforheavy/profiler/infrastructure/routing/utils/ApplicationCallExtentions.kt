package com.heavyforheavy.profiler.infrastructure.routing.utils

import com.heavyforheavy.profiler.infrastructure.model.TokenData
import com.heavyforheavy.profiler.infrastructure.model.asTokenData
import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import io.ktor.application.*
import io.ktor.auth.*

private fun ApplicationCall.getUserIdPrincipal(): UserIdPrincipal? {
  return principal()
}

private fun ApplicationCall.getToken(): String? {
  return request.headers["Authorization"]?.split(" ")?.takeIf { it.size > 1 }?.run { this[1] }
}

private fun ApplicationCall.requireToken(): String =
  getToken() ?: throw AuthException.InvalidToken()


fun ApplicationCall.getTokenData(): TokenData? =
  getUserIdPrincipal()?.name?.asTokenData(this.requireToken())

fun ApplicationCall.requireTokenData(): TokenData =
  getTokenData() ?: throw AuthException.InvalidToken()

fun ApplicationCall.getParameter(param: Param): String? = parameters[param.key]

fun ApplicationCall.requireParameter(param: Param): String =
  getParameter(param) ?: throw RequestException.ParameterNotFound(param)

fun ApplicationCall.requireQueryParameter(param: Param): String =
  request.queryParameters[param.key]!!
