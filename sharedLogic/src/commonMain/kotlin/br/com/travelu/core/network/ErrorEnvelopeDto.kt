package br.com.travelu.core.network

import kotlinx.serialization.Serializable

@Serializable
data class ErrorEnvelopeDto(
    val error: ErrorDto,
)

@Serializable
data class ErrorDto(
    val code: String,
    val message: String,
)
