package com.heavyforheavy.profiler.model

import com.heavyforheavy.profiler.model.extentions.instance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


@Serializable
data class UserService(
    @SerialName("id") val id: Int,
    @SerialName("serviceInfo") val serviceInfo: ServiceInfo = ServiceInfo(0, "", "", ""),
    @SerialName("userId") val userId: Int,
    @SerialName("userLink") val userLink: String,
)

fun UserService.asString(pretty: Boolean = false) = Json.instance(pretty).encodeToString(UserService.serializer(), this)

fun UserService.asJson(pretty: Boolean = false) =
    Json.instance(pretty).encodeToJsonElement(UserService.serializer(), this)

fun List<UserService>.asJson(pretty: Boolean = false) =
    Json.instance(pretty).encodeToJsonElement(ListSerializer(UserService.serializer()), this)