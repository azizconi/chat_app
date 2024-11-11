package com.example.domain.use_case

import com.example.domain.repository.DataStoreRepository
import com.example.domain.repository.ProfileRepository
import com.example.domain.repository.TokenRepository

class LogoutUseCase(
    private val tokenRepository: TokenRepository,
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke() {
        tokenRepository.clear()
        profileRepository.clearLocalProfile()
    }

}