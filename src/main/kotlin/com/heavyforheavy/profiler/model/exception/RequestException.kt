package com.heavyforheavy.profiler.model.exception

import com.heavyforheavy.profiler.routes.Param
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModuleBuilder

sealed class RequestException : ApiException() {
    @Serializable
    data class ParameterNotFound(
        val parameter: Param,
        override val code: Int = 200
    ) : RequestException() {
        override val message: String
            get() = "Parameter ${parameter.key} not found"
    }

    @Serializable
    data class PermissionDenied(
        override val code: Int = 201,
        override val message: String = "not enough rights"
    ) : RequestException()

    @Serializable
    data class OperationFailed(
        override val code: Int = 202,
        override val message: String = "requested operation failed"
    ) : RequestException()

    @Serializable
    data class NotImplemented(
        override val code: Int = 203,
        override val message: String = "feature is not implemented",
    ) : RequestException() {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.NotImplemented
    }
}

internal fun SerializersModuleBuilder.requestExceptionPolymorphic() {
    polymorphic(
        ApiException::class,
        RequestException.ParameterNotFound::class,
        RequestException.ParameterNotFound.serializer()
    )
    polymorphic(
        ApiException::class,
        RequestException.PermissionDenied::class,
        RequestException.PermissionDenied.serializer()
    )
    polymorphic(
        ApiException::class,
        RequestException.OperationFailed::class,
        RequestException.OperationFailed.serializer()
    )
}