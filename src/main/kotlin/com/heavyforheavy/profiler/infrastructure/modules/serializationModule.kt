package com.heavyforheavy.profiler.infrastructure.modules

import com.heavyforheavy.profiler.model.customSerializersModule
import com.heavyforheavy.profiler.model.getJson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.SerializersModule


fun Application.serializationModule() {
  install(ContentNegotiation) {
    serialization(
      contentType = ContentType.Application.Json,
      format = object : StringFormat {
        override val serializersModule: SerializersModule
          get() = customSerializersModule

        override fun <T> decodeFromString(
          deserializer: DeserializationStrategy<T>,
          string: String
        ): T {
          return getJson().decodeFromString(deserializer, string)
        }

        override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String =
          getJson().encodeToString(serializer, value)
      }
    )
  }
}