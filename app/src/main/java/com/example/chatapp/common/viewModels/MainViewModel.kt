package com.example.chatapp.common.viewModels

import androidx.lifecycle.ViewModel
import com.example.domain.use_case.GetAccessTokenUseCase

class MainViewModel(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
): ViewModel() {
    val accessToken = getAccessTokenUseCase()

}