package com.heavyforheavy.profiler.infrastructure

import com.heavyforheavy.profiler.data.dataModule
import com.heavyforheavy.profiler.domain.usecase.useCasesModule
import com.heavyforheavy.profiler.infrastructure.modules.authModule
import com.heavyforheavy.profiler.infrastructure.modules.errorHandlerModule
import com.heavyforheavy.profiler.infrastructure.modules.serializationModule
import com.heavyforheavy.profiler.infrastructure.modules.supportModule
import com.heavyforheavy.profiler.infrastructure.routing.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger


fun Application.configurator(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {

    install(Koin) {
        //TODO set log level to deafult when koin will work fine with java version
        slf4jLogger(level = org.koin.core.logger.Level.ERROR)
        modules(dataModule, infrastructureModule, useCasesModule)
    }

    supportModule()
    serializationModule()
    authModule()
    errorHandlerModule()
    install(Locations) {
    }

    allRouting()
}

private fun Application.allRouting() {
    //Auth routings are located at authModule
    routing {
        homeRouting()

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