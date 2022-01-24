package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class Role(
  @SerialName("id") val id: Int = 0,
  @SerialName("name") val name: String? = null,
  @SerialName("createdAt") val createdAt: String? = null,
)

fun Role.asString() =
  getJson().encodeToString(Role.serializer(), this)

fun Role.asJson() =
  getJson().encodeToJsonElement(Role.serializer(), this)

fun List<Role>.asJson() =
  getJson().encodeToJsonElement(ListSerializer(Role.serializer()), this)