package com.example.chatapp.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.utils.asState
import com.example.core.Resource
import com.example.domain.interactor.send_auth_code.SendAuthCodeInteractor
import com.example.domain.use_case.SendAuthCodeUseCase
import kotlinx.coroutines.launch

class AuthScreenViewModel(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase,
) : ViewModel() {

    private val _sendAuthCodeResult =
        mutableStateOf<Resource<SendAuthCodeInteractor>>(Resource.Idle)
    val sendAuthCodeResult =  _sendAuthCodeResult.asState()


    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            sendAuthCodeUseCase(phone = phone).collect {
                _sendAuthCodeResult.value = it
            }
        }
    }

    fun resetSendAuthCodeResult() {
        _sendAuthCodeResult.value = Resource.Idle
    }

}