package com.example.chatapp.presentation.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.common.utils.asState
import com.example.core.Resource
import com.example.domain.interactor.edit_profile.EditProfileInteractor
import com.example.domain.interactor.profile.ProfileInteractor
import com.example.domain.interactor.send_edit_profile.SendEditProfileInteractor
import com.example.domain.use_case.EditProfileUseCase
import com.example.domain.use_case.GetProfileUseCase
import com.example.domain.use_case.LogoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _profileResult = mutableStateOf<Resource<ProfileInteractor>>(Resource.Idle)
    val profileResult = _profileResult.asState()

    private val _editProfileResult = mutableStateOf<Resource<ProfileInteractor>>(Resource.Idle)
    val editProfileResult = _editProfileResult.asState()

    fun getProfile(isLocalRequest: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            getProfileUseCase(isLocalRequest).collect {
                _profileResult.value = it
            }
        }
    }

    fun editProfile(interactor: SendEditProfileInteractor) {
        viewModelScope.launch(Dispatchers.IO) {
            editProfileUseCase(interactor).collect {
                _editProfileResult.value = it
                if (it is Resource.Success) {
                    _profileResult.value = it
                }
            }
        }
    }

    fun resetEditProfileResult() {
        _editProfileResult.value = Resource.Idle
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }

}