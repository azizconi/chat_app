package com.example.chatapp.presentation.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.utils.asState
import com.example.core.Resource
import com.example.domain.interactor.register.RegisterInteractor
import com.example.domain.use_case.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _registerResult = mutableStateOf<Resource<RegisterInteractor>>(Resource.Idle)
    val registerResult = _registerResult.asState()

    fun register(
        phone: String,
        username: String,
        name: String
    ) {
        viewModelScope.launch {
            registerUseCase(
                phone, username, name
            ).collect {
                _registerResult.value = it
            }
        }
    }




}