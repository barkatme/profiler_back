package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer


@Serializable
data class Permission(
  @SerialName("id") val id: Int = 0,
  @SerialName("name") val name: String? = null,
  @SerialName("createdAt") val createdAt: String? = null,
)

fun Permission.asString() =
  getJson().encodeToString(Permission.serializer(), this)

fun Permission.asJson() =
  getJson().encodeToJsonElement(Permission.serializer(), this)

fun List<Permission>.asJson() =
  getJson().encodeToJsonElement(ListSerializer(Permission.serializer()), this)
