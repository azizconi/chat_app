package com.example.domain.interactor.register

data class RegisterInteractor(
    val access_token: String,
    val refresh_token: String,
    val user_id: Int
)
