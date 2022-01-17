package com.heavyforheavy.profiler.model.exception

import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.modules.SerializersModuleBuilder


@Serializable
abstract class AuthException : ApiException() {

    @Serializable
    data class InvalidAuth(
        override val code: Int = 101,
        override val message: String = "Invalid email or password"
    ) : AuthException()

    @Serializable
    data class InvalidEmail(
        override val code: Int = 102,
        override val message: String = "Invalid email"
    ) : AuthException()

    @Serializable
    data class InvalidPassword(
        override val code: Int = 103,
        override val message: String = "Invalid password"
    ) : AuthException()

    @Serializable
    data class EmailAlreadyExists(
        override val code: Int = 104,
        override val message: String = "Such email already exists"
    ) : AuthException()

    @Serializable
    data class InvalidToken(
        override val code: Int = 105,
        override val message: String = "Invalid token",
        @Transient override val httpStatusCode: HttpStatusCode = HttpStatusCode.Unauthorized
    ) : AuthException()

    @Serializable
    data class InvalidService(
        override val code: Int = 106,
        override val message: String = "Invalid service"
    ) : AuthException()

    @Serializable
    data class InvalidUserId(
        override val code: Int = 107,
        override val message: String = "Such user does not exist"
    ) : AuthException()
}


internal fun SerializersModuleBuilder.validationExceptionPolymorphic() {
    polymorphic(
        ApiException::class,
        AuthException.InvalidAuth::class,
        AuthException.InvalidAuth.serializer()
    )
    polymorphic(
        ApiException::class,
        AuthException.InvalidEmail::class,
        AuthException.InvalidEmail.serializer()
    )
    polymorphic(
        ApiException::class,
        AuthException.InvalidPassword::class,
        AuthException.InvalidPassword.serializer()
    )
    polymorphic(
        ApiException::class,
        AuthException.EmailAlreadyExists::class,
        AuthException.EmailAlreadyExists.serializer()
    )
    polymorphic(
        ApiException::class,
        AuthException.InvalidToken::class,
        AuthException.InvalidToken.serializer()
    )
    polymorphic(
        ApiException::class,
        AuthException.InvalidService::class,
        AuthException.InvalidService.serializer()
    )
    polymorphic(
        ApiException::class,
        AuthException.InvalidUserId::class,
        AuthException.InvalidUserId.serializer()
    )
}