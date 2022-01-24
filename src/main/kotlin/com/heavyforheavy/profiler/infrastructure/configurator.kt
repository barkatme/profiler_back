package com.heavyforheavy.profiler.infrastructure

import com.heavyforheavy.profiler.data.dataModule
import com.heavyforheavy.profiler.domain.usecase.useCasesModule
import com.heavyforheavy.profiler.infrastructure.routing.*
import com.heavyforheavy.profiler.infrastructure.setup.authModule
import com.heavyforheavy.profiler.infrastructure.setup.errorHandlerModule
import com.heavyforheavy.profiler.infrastructure.setup.serializationModule
import com.heavyforheavy.profiler.infrastructure.setup.supportModule
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger


fun Application.configurator(testing: Boolean = false) {

  install(Koin) {
    //TODO set log level to deafult when koin will work fine with java version
    slf4jLogger(level = org.koin.core.logger.Level.ERROR)
    modules(dataModule, infrastructureModule, useCasesModule)
  }

  supportModule(testing)
  serializationModule(testing)
  authModule(testing)
  errorHandlerModule(testing)
  install(Locations) {
  }

  addAllRoutes(testing)
}

private fun Application.addAllRoutes(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {
  routing {
    homeRouting()

    authRouting()
    usersRouting()
    viewedUsersRouting()
    saveUserRoting()
    serviceInfoRouting()
    roleRouting()

    userServicesRouting()

    static("/static") {
      resources("static")
    }
  }
}