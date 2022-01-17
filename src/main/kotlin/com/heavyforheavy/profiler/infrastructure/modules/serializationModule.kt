package com.heavyforheavy.profiler.infrastructure.modules

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*


fun Application.serializationModule() {
  install(ContentNegotiation) {
    gson {
      setPrettyPrinting()
      disableHtmlEscaping()
    }
  }
}