package br.com.travelu.feature.register.data.datasource

import br.com.travelu.feature.login.data.model.UserDTO
import br.com.travelu.feature.register.data.model.RegisterRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

// Fala diretamente com a API documentada em localhost:8080/docs. Nenhuma rota
// aqui trata erro — isso é responsabilidade de safeApiCall, no repositório.
class RegisterRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun register(request: RegisterRequestDto): UserDTO =
        httpClient.post("api/v1/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
