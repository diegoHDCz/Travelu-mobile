package br.com.travelu.core.network

import br.com.travelu.feature.login.data.model.RefreshRequestDto
import br.com.travelu.feature.login.data.model.TokenPairDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single { AuthSessionManager(get()) }
    single {
        val session: AuthSessionManager = get()
        HttpClient {
            expectSuccess = true
            // Base de todas as rotas; os datasources usam caminhos relativos.
            defaultRequest {
                url(AppConfig.API_BASE_URL.trimEnd('/') + "/")
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    },
                )
            }
            // Anexa o access token em toda rota fora de /auth/ e, ao receber 401,
            // troca o par via /auth/refresh e repete a requisição uma vez.
            install(Auth) {
                bearer {
                    loadTokens {
                        session.accessToken?.let { BearerTokens(it, session.refreshToken) }
                    }
                    refreshTokens {
                        val refreshToken = session.refreshToken ?: return@refreshTokens null
                        try {
                            val tokens: TokenPairDto = client.post("api/v1/auth/refresh") {
                                markAsRefreshTokenRequest()
                                contentType(ContentType.Application.Json)
                                setBody(RefreshRequestDto(refreshToken = refreshToken))
                            }.body()
                            session.saveTokens(tokens.accessToken, tokens.refreshToken)
                            BearerTokens(tokens.accessToken, tokens.refreshToken)
                        } catch (e: ResponseException) {
                            // Refresh recusado (expirado/revogado): a sessão acabou.
                            // Erros de rede propagam sem apagar os tokens.
                            session.clear()
                            null
                        }
                    }
                    sendWithoutRequest { request -> "auth" !in request.url.pathSegments }
                }
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
}
