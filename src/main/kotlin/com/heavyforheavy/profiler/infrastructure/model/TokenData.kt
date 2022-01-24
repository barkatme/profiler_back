package com.heavyforheavy.profiler.infrastructure.model

import com.heavyforheavy.profiler.infrastructure.model.extentions.instance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json

@Serializable
data class TokenData(
  @SerialName("id") val id: Int,
  @SerialName("login") val login: String? = null,
  @SerialName("email") val email: String,
  @Transient val token: String = ""
)

fun TokenData.asString() =
  Json.instance(false).encodeToString(TokenData.serializer(), this)

fun String.asTokenData(tokenString: String) =
  Json.instance(false).decodeFromString(TokenData.serializer(), this)
    .copy(
      token = tokenString
    )

fun User.tokenData() = TokenData(
  id = id,
  login = login,
  email = email
)