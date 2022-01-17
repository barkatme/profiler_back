package com.heavyforheavy.profiler.model.exception

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModuleBuilder

sealed class ServiceInfoException : ApiException() {
  @Serializable
  data class ServiceInfoNotFound(
    override val code: Int = 200,
    override val message: String = "ServiceInfo not found"
  ) : AuthException()
}

internal fun SerializersModuleBuilder.serviceInfoExceptionPolymorphic() {
  polymorphic(
    ApiException::class,
    ServiceInfoException.ServiceInfoNotFound::class,
    ServiceInfoException.ServiceInfoNotFound.serializer()
  )
}