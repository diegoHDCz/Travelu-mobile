package br.com.travelu.core.network

// Cada plataforma resolve "localhost" de um jeito diferente em dev: o
// emulador Android não compartilha a rede do host, então precisa do alias
// 10.0.2.2; simulador iOS e device físico via Mac compartilham a rede do Mac.
expect val API_BASE_URL: String
