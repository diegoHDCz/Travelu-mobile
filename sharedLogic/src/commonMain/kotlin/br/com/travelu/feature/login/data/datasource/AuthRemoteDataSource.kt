package br.com.travelu.feature.login.data.datasource

import br.com.travelu.feature.login.data.model.LoginRequestDto
import br.com.travelu.feature.login.data.model.LogoutRequestDto
import br.com.travelu.feature.login.data.model.RefreshRequestDto
import br.com.travelu.feature.login.data.model.TokenPairDto
import br.com.travelu.feature.login.data.model.UserDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

// Fala diretamente com a API documentada em localhost:8080/docs. Nenhuma rota
// aqui trata erro — isso é responsabilidade de safeApiCall, no repositório.
class AuthRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun login(request: LoginRequestDto): TokenPairDto =
        httpClient.post("api/v1/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun refresh(request: RefreshRequestDto): TokenPairDto =
        httpClient.post("api/v1/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun logout(request: LogoutRequestDto) {
        httpClient.post("api/v1/auth/logout") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    // O Authorization vem do plugin Auth (NetworkModule), que renova o token no 401.
    suspend fun getCurrentUser(): UserDTO =
        httpClient.get("api/v1/users/me").body()

    // O plugin Auth guarda em cache o último par carregado; após login/logout
    // ele precisa reler o AuthSessionManager.
    fun invalidateCachedTokens() {
        httpClient.authProvider<BearerAuthProvider>()?.clearToken()
    }
}
