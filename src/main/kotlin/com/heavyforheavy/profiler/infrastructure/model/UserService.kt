package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer


@Serializable
data class UserService(
  @SerialName("id") val id: Int,
  @SerialName("serviceInfo") val serviceInfo: ServiceInfo = ServiceInfo(0, "", "", ""),
  @SerialName("userId") val userId: Int? = null,
  @SerialName("userLink") val userLink: String? = null,
)

fun UserService.asString() =
  getJson().encodeToString(UserService.serializer(), this)

fun UserService.asJson() =
  getJson().encodeToJsonElement(UserService.serializer(), this)

fun List<UserService>.asJson() =
  getJson().encodeToJsonElement(ListSerializer(UserService.serializer()), this)