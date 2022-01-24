package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonElement

@Serializable
data class User(
  @SerialName("id") val id: Int = 0,
  @SerialName("login") val login: String? = null,
  @Transient val isDeleted: Boolean = false,
  @SerialName("firstName") val firstName: String? = null,
  @SerialName("lastName") val lastName: String? = null,
  @SerialName("about") val about: String? = null,
  @SerialName("email") val email: String,
  @SerialName("online") val isOnline: Boolean? = null,
  @SerialName("role") val role: Int? = null,
  @SerialName("dateRegistration") val dateRegistration: String? = null,

  @Transient val passwordHash: String? = null,
  @Transient var token: String? = null
)

fun User.asString() =
  getJson().encodeToString(User.serializer(), this)

fun User.asJson() =
  getJson().encodeToJsonElement(User.serializer(), this)

fun JsonElement.asUser() =
  getJson().decodeFromJsonElement(User.serializer(), this)

fun List<User>.asJson() =
  getJson().encodeToJsonElement(ListSerializer(User.serializer()), this)