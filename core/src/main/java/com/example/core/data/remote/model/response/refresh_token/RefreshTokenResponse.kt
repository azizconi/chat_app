package com.example.core.data.remote.model.response.refresh_token

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: String
)
