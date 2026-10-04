package br.com.travelu.feature.login.data.datasource

import br.com.travelu.core.network.API_BASE_URL
import br.com.travelu.feature.login.data.model.LoginRequestDto
import br.com.travelu.feature.login.data.model.LogoutRequestDto
import br.com.travelu.feature.login.data.model.RefreshRequestDto
import br.com.travelu.feature.login.data.model.RegisterRequestDto
import br.com.travelu.feature.login.data.model.TokenPairDto
import br.com.travelu.feature.login.data.model.UserDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

// Fala diretamente com a API documentada em localhost:8080/docs. Nenhuma rota
// aqui trata erro — isso é responsabilidade de safeApiCall, no repositório.
class AuthRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun register(request: RegisterRequestDto): UserDTO =
        httpClient.post("${API_BASE_URL}/api/v1/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun login(request: LoginRequestDto): TokenPairDto =
        httpClient.post("${API_BASE_URL}/api/v1/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun refresh(request: RefreshRequestDto): TokenPairDto =
        httpClient.post("${API_BASE_URL}/api/v1/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun logout(request: LogoutRequestDto) {
        httpClient.post("${API_BASE_URL}/api/v1/auth/logout") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    suspend fun getCurrentUser(accessToken: String): UserDTO =
        httpClient.get("${API_BASE_URL}/api/v1/users/me") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body()
}
