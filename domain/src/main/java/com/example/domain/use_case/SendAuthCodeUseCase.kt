package com.example.domain.use_case

import com.example.domain.repository.AuthRepository

class SendAuthCodeUseCase(
    private val repository: AuthRepository,
) {

    operator fun invoke(phone: String) = repository.sendAuthCode(phone)

}