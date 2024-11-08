package com.example.data.remote.model.response.register

import com.example.domain.interactor.register.RegisterInteractor

data class RegisterResponse(
    val access_token: String,
    val refresh_token: String,
    val user_id: Int,
) {
    fun toInteractor() = RegisterInteractor(
        access_token = access_token,
        refresh_token = refresh_token,
        user_id = user_id,
    )
}