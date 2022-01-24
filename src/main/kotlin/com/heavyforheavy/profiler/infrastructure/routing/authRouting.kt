package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.auth.*
import com.heavyforheavy.profiler.infrastructure.model.Credentials
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Application.authRouting() {
  routing {
    val signInUseCase: SignInUseCase = get()
    val signUpUseCase: SignUpUseCase = get()
    val signOutUseCase: SignOutUseCase = get()

    route(ProfilerRoute.AUTH_SIGN_IN) {
      val credentials = call.receive<Credentials>()
      val result = signInUseCase.invoke(SignInAction(credentials))
      call.respond(result.token.response())
    }

    route(ProfilerRoute.AUTH_SIGN_UP) {
      val credentials = call.receive<Credentials>()
      val result = signUpUseCase.invoke(SignUpAction(credentials))
      call.respond(result.token.response())
    }

    route(ProfilerRoute.AUTH_SIGN_OUT) {
      val tokenData = call.requireTokenData()
      val result = signOutUseCase.invoke(SignOutAction(tokenData))
      call.respond(result.token.response())
    }
  }
}