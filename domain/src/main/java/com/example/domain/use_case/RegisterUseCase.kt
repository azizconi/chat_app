package com.example.domain.use_case

import com.example.core.Resource
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.TokenRepository
import kotlinx.coroutines.flow.onEach

class RegisterUseCase(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository,
) {
    operator fun invoke(phone: String, username: String, name: String) = repository
        .register(phone = phone, username = username, name = name)
        .onEach { resource ->
            if (resource is Resource.Success) {
                resource.data.let { interactor ->
                    val accessToken = interactor.access_token
                    val refreshToken = interactor.refresh_token
                    val userId = interactor.refresh_token

                    tokenRepository.updateAccessToken(accessToken)
                    tokenRepository.updateRefreshToken(refreshToken)
                    tokenRepository.updateUserId(userId)
                }
            }
        }

}