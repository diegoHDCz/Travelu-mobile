package br.com.travelu.core.network

import platform.Foundation.NSBundle

// Lê a chave API_BASE_URL do Info.plist, que recebe o valor do xcconfig.
actual object AppConfig {
    actual val API_BASE_URL: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("API_BASE_URL") as? String
            ?: error("API_BASE_URL não definida no Info.plist")
}
