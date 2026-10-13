package br.com.travelu.feature.register.data.mapper

import br.com.travelu.feature.register.data.model.RegisterRequestDto
import br.com.travelu.feature.register.domain.model.Register

object RegisterRequestMapper {
    fun toDto(register: Register): RegisterRequestDto = RegisterRequestDto(
        name = register.name,
        password = register.password,
        email = register.email,
        phone = register.phone,
    )
}
