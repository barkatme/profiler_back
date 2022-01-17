package com.heavyforheavy.profiler.model

import com.google.gson.annotations.SerializedName
import com.heavyforheavy.profiler.mappers.response
import kotlinx.serialization.Serializable

@Serializable
data class OperationResult(
  @SerializedName("result") val result: Boolean
)

fun OperationResult.asString() =
  getJson().encodeToString(OperationResult.serializer(), this)

fun OperationResult.asJson() =
  getJson().encodeToJsonElement(OperationResult.serializer(), this)

fun Boolean.response() = OperationResult(this).response()