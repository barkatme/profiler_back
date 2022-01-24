package com.heavyforheavy.profiler.infrastructure.setup

import com.heavyforheavy.profiler.infrastructure.model.exception.AuthException
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.model.exception.response
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*

fun Application.errorHandlerModule(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {

  install(StatusPages) {

    exception<AuthException> { exception ->
      exception.printStackTrace()
      call.respond(exception.httpStatusCode, exception.response())
    }

    exception<Throwable> { exception ->
      val error = RequestException.OperationFailed(code = 0, message = exception.localizedMessage)
      exception.printStackTrace()
      call.respond(error.response())
    }

    exception<NotImplementedError> { exception ->
      val error = RequestException.OperationFailed(message = exception.localizedMessage)
      exception.printStackTrace()
      call.respond(error.response())
    }
  }
}