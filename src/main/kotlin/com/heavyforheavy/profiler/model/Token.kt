package com.heavyforheavy.profiler.model

import com.heavyforheavy.profiler.model.extentions.instance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable
data class Token(@SerialName("token") val token: String)

fun Token.asString(pretty: Boolean = false) =
  Json.instance(pretty).encodeToString(Token.serializer(), this)

fun Token.asJson(pretty: Boolean = false) =
  Json.instance(pretty).encodeToJsonElement(Token.serializer(), this)

fun List<Token>.asJson(pretty: Boolean = false) =
  Json.instance(pretty).encodeToJsonElement(ListSerializer(Token.serializer()), this)