package com.example.domain.use_case

import com.example.domain.interactor.send_edit_profile.SendEditProfileInteractor
import com.example.domain.repository.ProfileRepository
import com.example.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first

class EditProfileUseCase(
    private val repository: ProfileRepository,
    private val tokenRepository: TokenRepository,
) {

    suspend operator fun invoke(
        interactor: SendEditProfileInteractor,
    ) = repository.editProfile(
        tokenRepository.getAccessToken().first().toString(),
        interactor
    )

}