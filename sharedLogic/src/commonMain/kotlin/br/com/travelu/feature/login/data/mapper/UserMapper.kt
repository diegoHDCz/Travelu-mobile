package br.com.travelu.feature.login.data.mapper

import br.com.travelu.feature.login.data.model.UserDTO
import br.com.travelu.feature.login.domain.model.User

fun UserDTO.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    username = username,
)
