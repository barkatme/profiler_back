package com.heavyforheavy.profiler

import io.ktor.server.testing.*
import kotlin.test.Test

class ApplicationTest {
  @Test
  fun testRoot() {
    testApplication {
//            handleRequest(HttpMethod.Get, "/").apply {
//                assertEquals(HttpStatusCode.OK, response.status())
//                assertEquals("Hello World!", response.content)
//            }
    }
  }
}