package com.heavyforheavy.profiler.infrastructure.setup

import com.heavyforheavy.profiler.data.DatabaseFactory
import com.heavyforheavy.profiler.infrastructure.model.SimpleJWT
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.get


fun Application.authModule(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {
  DatabaseFactory.init()
  val simpleJwt: SimpleJWT = get()

  install(Authentication) {
    jwt {
      verifier(simpleJwt.verifier)
      validate {
        UserIdPrincipal(it.payload.getClaim("name").asString())
      }
      authSchemes()
    }
  }
}