package br.com.travelu.feature.login.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val username: String? = null,
)
