package com.example.core.data.remote.api

import com.example.core.data.remote.model.request.refresh_token.RefreshTokenRequest
import com.example.core.data.remote.model.response.refresh_token.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ApiService {

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

}