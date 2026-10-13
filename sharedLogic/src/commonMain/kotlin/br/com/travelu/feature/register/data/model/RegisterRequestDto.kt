package br.com.travelu.feature.register.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val name: String,
    val password: String,
    val email: String? = null,
    val phone: String? = null,
    val username: String? = null,
)
