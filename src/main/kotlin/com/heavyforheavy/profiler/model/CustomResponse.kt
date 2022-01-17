package com.heavyforheavy.profiler.model

import com.heavyforheavy.profiler.model.exception.ApiException
import com.heavyforheavy.profiler.model.exception.errorPolymorphic
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder

@Serializable
data class CustomResponse(
//    @Polymorphic
    @SerialName("data") val data: JsonElement? = null,
    @Polymorphic
    @SerialName("error") val error: ApiException? = null,
)

fun CustomResponse.asString(pretty: Boolean = false) = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = pretty
    serializersModule = SerializersModule {
        dataPolymorphic()
        errorPolymorphic()
    }
}.encodeToString(CustomResponse.serializer(), this)

fun SerializersModuleBuilder.dataPolymorphic() {
//    polymorphic(JsonElement::class) {
//        polymorphic(User::class, User.serializer())
//        polymorphic(ServiceInfo::class, ServiceInfo.serializer())
//        polymorphic(UserService::class, UserService.serializer())
//    }
}