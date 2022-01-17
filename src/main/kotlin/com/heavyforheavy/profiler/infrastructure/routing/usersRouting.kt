package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.user.GetOtherUserUseCase
import com.heavyforheavy.profiler.domain.usecase.user.GetUserByEmailUseCase
import com.heavyforheavy.profiler.domain.usecase.user.GetUserByTokenUseCase
import com.heavyforheavy.profiler.domain.usecase.user.UpdateUserUseCase
import com.heavyforheavy.profiler.infrastructure.routing.routes.getToken
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.routes.Param
import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Routing.usersRouting() {

    val getUserByEmailUseCase: GetUserByEmailUseCase = get()
    val getOtherUserUseCase: GetOtherUserUseCase = get()
    val getUserByTokenUseCase: GetUserByTokenUseCase = get()
    val updateUserUseCase: UpdateUserUseCase = get()

    route(Routes.USER) {
        call.respond(
            call.getUserIdPrincipal()?.name?.let { it1 -> getUserByEmailUseCase.getUser(it1) }?.response()
                ?: throw AuthException.InvalidToken()
        )
    }

    route(Routes.UPDATE_USER) {
        val currentUser = try {
            call.getUserIdPrincipal()?.name?.let { it1 -> getUserByEmailUseCase.getUser(it1) }
                ?: call.getToken()?.let {
                    getUserByTokenUseCase.getUser(it)
                }
        } catch (tokenException: AuthException.InvalidToken) {
            null
        }
        val updatedUser = call.receive<User>()
        call.respond(updateUserUseCase.updateUser(currentUser, updatedUser))
    }

    route(Routes.USER_BY_ID) {
        val currUid = try {
            call.getUserIdPrincipal()?.name?.let { it1 -> getUserByEmailUseCase.getUser(it1)?.id }
                ?: call.getToken()?.let {
                    getUserByTokenUseCase.getUser(it)?.id
                }
        } catch (tokenException: AuthException.InvalidToken) {
            null
        }
        val uid = call.requireParameter(Param.USER_ID).toInt()
        val user = getOtherUserUseCase.getUser(currUid, uid) ?: throw AuthException.InvalidUserId()
        call.respond(user.response())
    }
}