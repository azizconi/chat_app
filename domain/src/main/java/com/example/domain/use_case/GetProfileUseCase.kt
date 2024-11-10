package com.example.domain.use_case

import com.example.core.Resource
import com.example.domain.interactor.profile.ProfileInteractor
import com.example.domain.repository.ProfileRepository
import com.example.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetProfileUseCase(
    private val repository: ProfileRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke(isLocalRequest: Boolean): Flow<Resource<ProfileInteractor>> {
        return repository.getProfile(
            tokenRepository.getAccessToken().first().toString(),
            isLocalRequest = isLocalRequest
        )
    }
}