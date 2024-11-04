package com.example.core.domain.repository

import com.example.core.data.remote.model.request.refresh_token.RefreshTokenRequest
import com.example.core.data.remote.model.response.refresh_token.RefreshTokenResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

internal interface AuthRepository {
    suspend fun refreshAccessToken(refreshToken: String): Response<RefreshTokenResponse>
    fun getRefreshToken(): Flow<String?>
    fun getAccessToken(): Flow<String?>
    suspend fun updateRefreshToken(token: String)
    suspend fun updateAccessToken(token: String)
    suspend fun clear()
}