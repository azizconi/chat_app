package com.example.domain.interactor.refresh_token

data class RefreshTokenInteractor(
     val refreshToken: String,
     val accessToken: String,
     val userId: String
)
