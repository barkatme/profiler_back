package com.heavyforheavy.profiler.domain

import com.auth0.jwt.exceptions.TokenExpiredException
import com.heavyforheavy.profiler.infrastructure.model.*
import java.time.Instant
import java.util.*

class TokenManager(private val simpleJWT: SimpleJWT) {

  fun createToken(user: User): Token {
    return user.tokenData().asString().let {
      Token(simpleJWT.sign(it))
    }
  }

  fun checkToken(token: Token): Boolean {
    return try {
      val decodedJWT = simpleJWT.verifier.verify(token.token)
      decodedJWT.expiresAt?.let { date -> date > Date.from(Instant.now()) }
        ?: false
    } catch (e: TokenExpiredException) {
      false
    }
  }
}