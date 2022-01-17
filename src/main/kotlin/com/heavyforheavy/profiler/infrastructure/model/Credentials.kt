package com.heavyforheavy.profiler.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
  @SerialName("email") val email: String,
  @SerialName("password") val password: String
)