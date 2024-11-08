package com.example.data.remote.model.request.refresh_token

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest (
    @SerializedName("refresh_token") val refreshToken: String
)