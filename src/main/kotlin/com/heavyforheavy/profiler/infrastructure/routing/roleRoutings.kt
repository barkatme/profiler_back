package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.role.GetRolePermissionsAction
import com.heavyforheavy.profiler.domain.usecase.role.GetRolePermissionsUseCase
import com.heavyforheavy.profiler.domain.usecase.role.GetUserPermissionsAction
import com.heavyforheavy.profiler.domain.usecase.role.GetUserPermissionsUseCase
import com.heavyforheavy.profiler.infrastructure.model.response
import com.heavyforheavy.profiler.infrastructure.routing.models.Param
import com.heavyforheavy.profiler.infrastructure.routing.models.ProfilerRoute
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.utils.requireTokenData
import com.heavyforheavy.profiler.infrastructure.routing.utils.route
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get


fun Routing.roleRouting() {

  val userPermissionsUseCase: GetUserPermissionsUseCase = get()
  val rolePermissionsUseCase: GetRolePermissionsUseCase = get()

  route(ProfilerRoute.ROLE_PERMISSIONS) {
    val tokenData = call.requireTokenData()
    val action = GetUserPermissionsAction(tokenData.email)
    call.respond(userPermissionsUseCase.invoke(action).permissions.response())

  }

  route(ProfilerRoute.ROLE_PERMISSIONS_BY_ID) {
    val roleId = call.requireParameter(Param.ROLE_ID).toInt()
    val action = GetRolePermissionsAction(roleId)
    val result = rolePermissionsUseCase.invoke(action)
    call.respond(result.permissions.response())
  }
}