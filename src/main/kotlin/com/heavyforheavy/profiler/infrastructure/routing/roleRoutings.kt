package com.heavyforheavy.profiler.infrastructure.routing

import com.heavyforheavy.profiler.domain.usecase.role.GetRolePermissionsUseCase
import com.heavyforheavy.profiler.domain.usecase.role.GetUserRolePermissionsUseCase
import com.heavyforheavy.profiler.infrastructure.routing.routes.getUserIdPrincipal
import com.heavyforheavy.profiler.infrastructure.routing.routes.requireParameter
import com.heavyforheavy.profiler.infrastructure.routing.routes.route
import com.heavyforheavy.profiler.mappers.response
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.routes.Param
import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get


fun Routing.roleRouting() {

  val getUserRolePermissionsUseCase: GetUserRolePermissionsUseCase = get()
  val getRolePermissionsUseCase: GetRolePermissionsUseCase = get()

  route(Routes.ROLE_PERMISSIONS) {
    call.respond(
      call.getUserIdPrincipal()?.name?.let { email ->
        getUserRolePermissionsUseCase.getUserPermissions(email).response()
      } ?: throw AuthException.InvalidToken()
    )
  }

  route(Routes.ROLE_PERMISSIONS_BY_ID) {
    val roleId = call.requireParameter(Param.ROLE_ID).toInt()
    call.respond(getRolePermissionsUseCase.getUserPermissions(roleId).response())
  }
}