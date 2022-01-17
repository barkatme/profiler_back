package com.heavyforheavy.profiler.infrastructure.routing.routes

import com.heavyforheavy.profiler.routes.Routes
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*


fun Route.route(route: Routes, builder: PipelineInterceptor<Unit, ApplicationCall>): Route {
  return if (route.requireAuth) {
    authenticate {
      buildRoute(route, builder)
    }
  } else {
    buildRoute(route, builder)
  }
}

private fun Route.buildRoute(
  route: Routes,
  builder: PipelineInterceptor<Unit, ApplicationCall>
): Route {
  return when (route.method) {
    HttpMethod.Get -> get(route.url, builder)
    HttpMethod.Post -> post(route.url, builder)
    HttpMethod.Delete -> delete(route.url, builder)
    HttpMethod.Put -> put(route.url, builder)
    HttpMethod.Head -> head(route.url, builder)
    HttpMethod.Options -> options(route.url, builder)
    HttpMethod.Patch -> patch(route.url, builder)
    else -> get(route.url, builder)
  }
}