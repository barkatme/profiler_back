package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.model.CustomResponse
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.UserService
import com.heavyforheavy.profiler.model.exception.AuthException
import com.heavyforheavy.profiler.model.exception.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class GetUserAndServicesUseCase(
    private val serviceRepository: UserServiceRepository,
    private val userRepository: UserRepository
) {

    @Serializable
    data class UserAndServices(
        @SerialName("user") val user: User,
        @SerialName("services") val services: List<UserService>
    )

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun getServices(userId: Int) = withContext(Dispatchers.IO) {
        val user = userRepository.getById(userId)
            ?: throw RequestException.OperationFailed(message = "user with such id wasn't found")
        getServices(user)
    }

    suspend fun getServices(userEmail: String?) = withContext(Dispatchers.IO) {
        val user = userEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidEmail()
        getServices(user)
    }

    private suspend fun getServices(user: User) = withContext(Dispatchers.IO) {
        val services = serviceRepository.getByUserId(user.id)
        UserAndServices(user, services)
    }
}




fun GetUserAndServicesUseCase.UserAndServices.asString(pretty: Boolean = false) = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = pretty
}.encodeToString(GetUserAndServicesUseCase.UserAndServices.serializer(), this)

fun GetUserAndServicesUseCase.UserAndServices.asJson(pretty: Boolean = false) = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = pretty
}.encodeToJsonElement(GetUserAndServicesUseCase.UserAndServices.serializer(), this)

fun List<GetUserAndServicesUseCase.UserAndServices>.asJson(pretty: Boolean = false) = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = pretty
}.encodeToJsonElement(ListSerializer(GetUserAndServicesUseCase.UserAndServices.serializer()), this)

fun GetUserAndServicesUseCase.UserAndServices.response() = CustomResponse(this.asJson())

@JvmName("userAndServices")
fun List<GetUserAndServicesUseCase.UserAndServices>.response() = CustomResponse(this.asJson())