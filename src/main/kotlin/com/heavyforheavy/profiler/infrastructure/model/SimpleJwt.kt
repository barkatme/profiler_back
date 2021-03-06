package com.heavyforheavy.profiler.infrastructure.model

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class SimpleJWT(secret: String) {
  private val validityInMs = 36_000_00 * 1
  private val algorithm = Algorithm.HMAC256(secret)

  val verifier: JWTVerifier = JWT.require(algorithm).build()

  fun sign(email: String): String = JWT.create()
    .withClaim("name", email)
    .withExpiresAt(getExpiration())
    .sign(algorithm)

  private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}