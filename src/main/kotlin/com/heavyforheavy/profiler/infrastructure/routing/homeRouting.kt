package com.heavyforheavy.profiler.infrastructure.routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

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