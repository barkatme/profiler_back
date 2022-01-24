package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class ServiceInfo(
  @SerialName("id") val id: Int = 0,
  @SerialName("link") val link: String? = null,
  @SerialName("name") val name: String? = null,
  @SerialName("image") val image: String? = null,
)

fun ServiceInfo.asString() =
  getJson().encodeToString(ServiceInfo.serializer(), this)

fun ServiceInfo.asJson() =
  getJson().encodeToJsonElement(ServiceInfo.serializer(), this)

fun List<ServiceInfo>.asJson() =
  getJson().encodeToJsonElement(ListSerializer(ServiceInfo.serializer()), this)