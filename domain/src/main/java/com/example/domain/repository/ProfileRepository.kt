package com.example.domain.repository

import com.example.core.Resource
import com.example.domain.interactor.edit_profile.EditProfileInteractor
import com.example.domain.interactor.profile.ProfileInteractor
import com.example.domain.interactor.send_edit_profile.SendEditProfileInteractor
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(token: String, isLocalRequest: Boolean): Flow<Resource<ProfileInteractor>>
    fun editProfile(token: String, interactor: SendEditProfileInteractor): Flow<Resource<ProfileInteractor>>
}