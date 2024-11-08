package com.example.chatapp.presentation.otp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.utils.asState
import com.example.core.Resource
import com.example.domain.interactor.check_auth_code.CheckAuthCodeInteractor
import com.example.domain.use_case.CheckAuthCodeUseCase
import kotlinx.coroutines.launch

class OtpScreenViewModel(private val checkAuthCodeUseCase: CheckAuthCodeUseCase): ViewModel() {

    private val _otpResult = mutableStateOf<Resource<CheckAuthCodeInteractor>>(Resource.Idle)
    val otpResult = _otpResult.asState()

    fun checkAuthCode(phone: String, code: String) {
        viewModelScope.launch {
            checkAuthCodeUseCase(phone = phone, code = code).collect {
                _otpResult.value = it
            }
        }
    }

    fun resetCheckAuthCodeResult() {
        _otpResult.value = Resource.Idle
    }

}