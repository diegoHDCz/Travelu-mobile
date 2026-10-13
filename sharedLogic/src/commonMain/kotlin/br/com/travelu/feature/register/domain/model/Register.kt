package br.com.travelu.feature.register.domain.model

data class Register(
    val name: String,
    val password: String,
    val email: String? = null,
    val phone: String? = null,
)
