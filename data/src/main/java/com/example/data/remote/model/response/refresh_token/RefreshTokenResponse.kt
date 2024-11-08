package com.example.data.remote.model.response.refresh_token

import com.example.domain.interactor.refresh_token.RefreshTokenInteractor
import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: String
) {
    fun toInteractor() = RefreshTokenInteractor(
        refreshToken = refreshToken, accessToken = accessToken, userId = userId
    )
}
