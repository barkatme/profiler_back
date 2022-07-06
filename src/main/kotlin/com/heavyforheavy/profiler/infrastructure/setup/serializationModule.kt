package com.heavyforheavy.profiler.infrastructure.setup

import com.heavyforheavy.profiler.infrastructure.model.customSerializersModule
import com.heavyforheavy.profiler.infrastructure.model.getJson
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.SerializersModule


fun Application.serializationModule(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {
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