package br.com.travelu.core.network

// Configuração de ambiente do app. O valor nasce no `.env` da raiz e chega em
// cada plataforma pelo mecanismo nativo dela: BuildConfig do androidApp no
// Android, Info.plist (via xcconfig) no iOS.
expect object AppConfig {
    val API_BASE_URL: String
}
