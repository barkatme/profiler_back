package com.heavyforheavy.profiler.infrastructure.setup

import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.model.exception.response
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.errorHandlerModule(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {

  install(StatusPages) {

    exception<AuthException> { call, exception ->
      exception.printStackTrace()
      call.respond(exception.httpStatusCode, exception.response())
    }

    exception<Throwable> { call, exception ->
      val error = RequestException.OperationFailed(code = 0, message = exception.localizedMessage)
      exception.printStackTrace()
      call.respond(error.response())
    }

    exception<NotImplementedError> { call, exception ->
      val error = RequestException.OperationFailed(message = exception.localizedMessage)
      exception.printStackTrace()
      call.respond(error.response())
    }
  }
}