package com.heavyforheavy.profiler.infrastructure.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("DEPRECATION")
fun Routing.homeRouting() {
  get("/") {
    call.respondText(
      this::class.java.classLoader.getResource("index.html")!!.readText(),
      ContentType.Text.Html
    )
  }
  static("/") {
    resources("")
  }
}