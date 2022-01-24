package com.heavyforheavy.profiler.infrastructure.setup

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.server.engine.*
import org.slf4j.event.Level

fun Application.supportModule(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {
  install(CallLogging) {
    level = Level.INFO
    filter { call -> call.request.path().startsWith("/") }
  }

  install(CORS) {
    method(HttpMethod.Options)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Patch)
    header(HttpHeaders.Authorization)
    header("MyCustomHeader")
//        allowCredentials = true
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
  }


  install(ShutDownUrl.ApplicationCallFeature) {
    // The URL that will be intercepted (you can also use the application.conf's ktor.deployment.shutdown.url key)
    shutDownUrl = "/ktor/application/shutdown"
    // A function that will be executed to get the exit code of the process
    exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
  }
}