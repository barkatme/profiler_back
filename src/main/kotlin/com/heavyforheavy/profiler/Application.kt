package com.heavyforheavy.profiler

import com.heavyforheavy.profiler.infrastructure.configurator
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    var started = false
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, host = "0.0.0.0") {
        if (!started) {
            configurator(false)
            started = true
        }
    }.start(wait = true)
}
