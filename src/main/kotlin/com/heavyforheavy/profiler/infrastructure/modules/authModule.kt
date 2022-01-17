package com.heavyforheavy.profiler.infrastructure.modules

import com.auth0.jwt.exceptions.TokenExpiredException
import com.heavyforheavy.profiler.data.DatabaseFactory
import com.heavyforheavy.profiler.domain.usecase.auth.SignInUseCase
import com.heavyforheavy.profiler.domain.usecase.auth.SignOutUseCase
import com.heavyforheavy.profiler.domain.usecase.auth.SignUpUseCase
import com.heavyforheavy.profiler.domain.usecase.user.GetUserByEmailUseCase
import com.heavyforheavy.profiler.infrastructure.model.Credentials
import com.heavyforheavy.profiler.infrastructure.model.SimpleJWT
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.model.Token
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.RequestException
import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get
import java.time.Instant
import java.util.*


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

    routing {

        val signInUseCase: SignInUseCase = get()
        val signUpUseCase: SignUpUseCase = get()
        val signOutUseCase: SignOutUseCase = get()
        val getUserByEmailUseCase: GetUserByEmailUseCase = get()

        route(Routes.AUTH_SIGN_IN) {
            val post = call.receive<Credentials>()
            val token = signInUseCase.signIn(
                post.email,
                post.password,
                tokenChecker = {
                    try {
                        val decodedJWT = simpleJwt.verifier.verify(it)
                        decodedJWT.expiresAt?.let { date -> date > Date.from(Instant.now()) }
                            ?: false
                    } catch (e: TokenExpiredException) {
                        false
                    }
                },
                tokenGenerator = { simpleJwt.sign(post.email) }
            )
            call.respond(Token(token).response())
        }

        route(Routes.AUTH_SIGN_UP) {
            val post = call.receive<Credentials>()
            val userWithEmailAndPassword = User(email = post.email, passwordHash = post.password)
            val resultUser = signUpUseCase.signUp(userWithEmailAndPassword) {
                simpleJwt.sign(userWithEmailAndPassword.email)
            }
            val token = resultUser.token
                ?: throw RequestException.OperationFailed(message = "new user has NULL token")
            call.respond(Token(token).response())
        }

        route(Routes.AUTH_SIGN_OUT) {
            val currentUser =
                call.getUserIdPrincipal()?.name?.let { email -> getUserByEmailUseCase.getUser(email) }
                    ?: throw AuthException.InvalidToken()
            val newToken =
                signOutUseCase.signOut(currentUser.email) { simpleJwt.sign(currentUser.email) }
            call.respond(Token(newToken).response())
        }
    }
}