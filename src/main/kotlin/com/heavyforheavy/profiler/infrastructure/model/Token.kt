package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class Token(@SerialName("token") val token: String)

fun Token.asString() =
  getJson().encodeToString(Token.serializer(), this)

fun Token.asJson() =
  getJson().encodeToJsonElement(Token.serializer(), this)

fun List<Token>.asJson() =
  getJson().encodeToJsonElement(ListSerializer(Token.serializer()), this)