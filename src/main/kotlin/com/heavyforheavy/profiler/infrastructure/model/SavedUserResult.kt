package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class SavedUserResult(
  @SerialName("user") val user: User,
  @SerialName("saveDate") val saveDate: String
)


fun SavedUserResult.asString() =
  getJson().encodeToString(SavedUserResult.serializer(), this)

fun SavedUserResult.asJson() =
  getJson().encodeToJsonElement(SavedUserResult.serializer(), this)

fun List<SavedUserResult>.toJson() =
  getJson().encodeToJsonElement(
    ListSerializer(SavedUserResult.serializer()),
    this
  )