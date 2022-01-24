package com.heavyforheavy.profiler.infrastructure.model.exception

import com.heavyforheavy.profiler.infrastructure.model.CustomResponse
import io.ktor.http.*
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder

abstract class ApiException : Throwable() {
  @SerialName("code")
  abstract val code: Int

  @SerialName("message")
  abstract override val message: String

  @Transient
  open val httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest

}

private val ApiExceptionsSerializersModule by lazy {
  SerializersModule {
    errorPolymorphic()
  }
}

fun SerializersModuleBuilder.errorPolymorphic() {
  validationExceptionPolymorphic()
  requestExceptionPolymorphic()
  databaseExceptionPolymorphic()
  serviceInfoExceptionPolymorphic()
}

private val json = Json {
  serializersModule = ApiExceptionsSerializersModule
  encodeDefaults = true
}

fun ApiException.asString(): String {
  return json.encodeToString(PolymorphicSerializer(ApiException::class), this)
}

@Suppress("unused")
fun String.asError(): ApiException {
  return json.decodeFromString(PolymorphicSerializer(ApiException::class), this)
}

fun ApiException.response() = CustomResponse(null, this)