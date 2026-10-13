package br.com.travelu.core.network

// O plugin KMP de library do AGP não gera BuildConfig, então quem tem o valor
// é o BuildConfig do androidApp, que o repassa aqui via init() antes do Koin.
actual object AppConfig {
    private var apiBaseUrl: String? = null

    fun init(apiBaseUrl: String) {
        this.apiBaseUrl = apiBaseUrl
    }

    actual val API_BASE_URL: String
        get() = checkNotNull(apiBaseUrl) {
            "AppConfig.init() precisa ser chamado antes de usar API_BASE_URL"
        }
}
