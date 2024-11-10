package com.example.domain.use_case

import android.util.Log
import com.example.core.Resource
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.TokenRepository
import kotlinx.coroutines.flow.onEach

class CheckAuthCodeUseCase(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository,
) {
    operator fun invoke(phone: String, code: String) = repository
        .checkAuthCode(phone, code)
        .onEach { resource ->
            if (resource is Resource.Success) {
                resource.data.let { interactor ->
                    val accessToken = interactor.access_token
                    val refreshToken = interactor.refresh_token

                    Log.e("TAG", "invoke: $interactor", )

                    tokenRepository.updateAccessToken(accessToken)
                    tokenRepository.updateRefreshToken(refreshToken)
                }
            }
        }

}