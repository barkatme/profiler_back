package com.heavyforheavy.profiler.model

import com.heavyforheavy.profiler.model.extentions.instance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

@Serializable
data class User(
  @SerialName("id") val id: Int = 0,
  @SerialName("login") val login: String? = null,
  @Transient val isDeleted: Boolean = false,
  @SerialName("firstName") val firstName: String? = null,
  @SerialName("lastName") val lastName: String? = null,
  @SerialName("about") val about: String? = null,
  @SerialName("email") val email: String? = null,
  @SerialName("online") val isOnline: Boolean? = null,
  @SerialName("role") val role: Int? = null,
  @SerialName("dateRegistration") val dateRegistration: String? = null,

  @Transient val passwordHash: String? = null,
  @Transient var token: String? = null
)

fun User.asString(pretty: Boolean = false) =
    Json.instance(pretty).encodeToString(User.serializer(), this)

fun User.asJson(pretty: Boolean = false) =
    Json.instance(pretty).encodeToJsonElement(User.serializer(), this)

fun JsonElement.asUser(pretty: Boolean = false) =
    Json.instance(pretty).decodeFromJsonElement(User.serializer(), this)

fun List<User>.asJson(pretty: Boolean = false) =
    Json.instance(pretty).encodeToJsonElement(ListSerializer(User.serializer()), this)