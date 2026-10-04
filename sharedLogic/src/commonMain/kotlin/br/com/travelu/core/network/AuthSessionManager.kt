package br.com.travelu.core.network

// Guarda o par de tokens em memória. Some ao fechar o processo — ainda não há
// persistência (keychain/DataStore); quando entrar, troca-se apenas esta classe.
class AuthSessionManager {
    var accessToken: String? = null
        private set
    var refreshToken: String? = null
        private set

    val isAuthenticated: Boolean
        get() = accessToken != null

    fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    fun clear() {
        accessToken = null
        refreshToken = null
    }
}
