package com.heavyforheavy.profiler.model

import com.heavyforheavy.profiler.model.extentions.instance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable
data class ServiceInfo(
  @SerialName("id") val id: Int = 0,
  @SerialName("link") val link: String,
  @SerialName("name") val name: String,
  @SerialName("image") val image: String,
)

fun ServiceInfo.asString(pretty: Boolean = false) =
  Json.instance(pretty).encodeToString(ServiceInfo.serializer(), this)

fun ServiceInfo.asJson(pretty: Boolean = false) =
  Json.instance(pretty).encodeToJsonElement(ServiceInfo.serializer(), this)

fun List<ServiceInfo>.asJson(pretty: Boolean = false) =
  Json.instance(pretty).encodeToJsonElement(ListSerializer(ServiceInfo.serializer()), this)