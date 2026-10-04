package br.com.travelu.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val username: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
)
