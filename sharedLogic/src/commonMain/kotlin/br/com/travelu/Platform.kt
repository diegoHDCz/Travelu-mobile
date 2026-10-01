package br.com.travelu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform