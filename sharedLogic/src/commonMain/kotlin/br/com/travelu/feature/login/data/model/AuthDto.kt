package br.com.travelu.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val name: String,
    val password: String,
    val email: String? = null,
    val phone: String? = null,
    val username: String? = null,
)

@Serializable
data class LoginRequestDto(
    val login: String,
    val password: String,
    @SerialName("device_token") val deviceToken: String? = null,
)

@Serializable
data class RefreshRequestDto(
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("device_token") val deviceToken: String? = null,
)

@Serializable
data class LogoutRequestDto(
    @SerialName("refresh_token") val refreshToken: String,
)

@Serializable
data class TokenPairDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("expires_in") val expiresIn: Long,
)
