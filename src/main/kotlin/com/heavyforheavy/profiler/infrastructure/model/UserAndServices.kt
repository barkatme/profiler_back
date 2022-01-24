package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class UserAndServices(
  @SerialName("user") val user: User,
  @SerialName("services") val services: List<UserService>
)


fun UserAndServices.asString() =
  getJson().encodeToString(UserAndServices.serializer(), this)

fun UserAndServices.asJson() =
  getJson().encodeToJsonElement(UserAndServices.serializer(), this)

fun List<UserAndServices>.asJson() =
  getJson().encodeToJsonElement(
    ListSerializer(UserAndServices.serializer()),
    this
  )