package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.auth.*
import com.heavyforheavy.profiler.domain.usecase.email.SendEmailAction
import com.heavyforheavy.profiler.domain.usecase.email.SendEmailUseCase
import com.heavyforheavy.profiler.domain.usecase.user.*
import com.heavyforheavy.profiler.infrastructure.model.Credentials
import com.heavyforheavy.profiler.infrastructure.model.exception.RequestException
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireQueryParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get
import kotlin.random.Random

fun Application.authRouting() {
  routing {
    val signInUseCase: SignInUseCase = get()
    val signUpUseCase: SignUpUseCase = get()
    val signOutUseCase: SignOutUseCase = get()
    val sendEmailUseCase: SendEmailUseCase = get()
    val getUserUseCase: GetUserUseCase = get()
    val updateUserPasswordUseCase: UpdateUserPasswordUseCase = get()
    val getUserByEmailUseCase: GetUserByEmailUseCase = get()

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

      sendEmailUseCase.invoke(
        SendEmailAction(
          subject = "Profiler Logout",
          text = "hey you've logged out on all devices",
          targetUser = getUserUseCase.invoke(GetUserAction(tokenData.id)).user
        )
      )
    }

    val passwordCode = Random.nextInt(100000, 999999)

    route(ProfilerRoute.AUTH_FORGOT_PASSWORD) {
      val email = call.requireParameter(Param.EMAIL)
      val user = getUserByEmailUseCase.invoke(GetUserByEmailAction(email)).user
      //TODO need table which will store temp restore password codes to insert and check here
      sendEmailUseCase.invoke(
        SendEmailAction(
          subject = "Profiler Code",
          text = "insert this code - $passwordCode",
          targetUser = user
        )
      )
      call.respond(true.response())
    }

    route(ProfilerRoute.AUTH_RESTORE_PASSWORD) {
      val email = call.requireQueryParameter(Param.EMAIL)
      val code = call.requireQueryParameter(Param.CODE).toInt()
      val newPassword = call.requireQueryParameter(Param.NEW_PASSWORD)
      val user = getUserByEmailUseCase.invoke(GetUserByEmailAction(email)).user

      if (code != passwordCode) {
        throw RequestException.OperationFailed(message = "entered wrong code for restoring password")
      }

      val result = updateUserPasswordUseCase.invoke(UpdateUserPasswordAction(user, newPassword))

      call.respond(result.token.response())
    }

  }
}