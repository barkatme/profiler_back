package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class ViewerResult(
  @SerialName("user") val user: User,
  @SerialName("lastViewDate") val lastViewDate: String
)


fun ViewerResult.asString() =
  getJson().encodeToString(ViewerResult.serializer(), this)

fun ViewerResult.asJson() =
  getJson().encodeToJsonElement(ViewerResult.serializer(), this)

fun List<ViewerResult>.asJson() =
  getJson().encodeToJsonElement(
    ListSerializer(ViewerResult.serializer()),
    this
  )
