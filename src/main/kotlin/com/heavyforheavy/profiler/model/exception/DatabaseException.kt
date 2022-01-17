package com.heavyforheavy.profiler.model.exception

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModuleBuilder


@Serializable
sealed class DatabaseException : ApiException() {
  @Serializable
  data class OperationFailed(
    val description: String? = null,
    override val code: Int = 300
  ) : RequestException() {
    override val message: String
      get() = "Database Operation failed" + if (description != null) {
        "\n$description"
      } else {
        ""
      }
  }
}

internal fun SerializersModuleBuilder.databaseExceptionPolymorphic() {
  polymorphic(
    ApiException::class,
    DatabaseException.OperationFailed::class,
    DatabaseException.OperationFailed.serializer()
  )
}