package br.com.travelu.core.network

import br.com.travelu.core.util.Result
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

// Centraliza o tratamento de erro das chamadas HTTP: a API sempre responde
// erros no formato ErrorEnvelope ({ error: { code, message } }); aqui a
// mensagem é extraída para o Result.Error que o resto do app já entende.
suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: ResponseException) {
        val message = runCatching {
            Json.decodeFromString<ErrorEnvelopeDto>(e.response.bodyAsText()).error.message
        }.getOrNull() ?: e.message
        Result.Error(message ?: "Unknown error")
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }
}
