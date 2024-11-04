package com.example.core.data.repository

import com.example.core.data.remote.api.ApiService
import com.example.core.data.remote.model.request.refresh_token.RefreshTokenRequest
import com.example.core.data.remote.model.response.refresh_token.RefreshTokenResponse
import com.example.core.domain.repository.AuthRepository
import com.example.core.domain.repository.DataStoreRepository
import com.example.core.utils.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

internal class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val dataStoreRepository: DataStoreRepository,
) : AuthRepository {
    override suspend fun refreshAccessToken(refreshToken: String): Response<RefreshTokenResponse> {
        return apiService.refreshToken(RefreshTokenRequest(refreshToken))
    }

    override fun getRefreshToken(): Flow<String?> =
        dataStoreRepository.getString(PreferenceKeys.REFRESH_TOKEN)

    override fun getAccessToken(): Flow<String?> =
        dataStoreRepository.getString(PreferenceKeys.ACCESS_TOKEN)

    override suspend fun updateRefreshToken(token: String) {
        dataStoreRepository.save(PreferenceKeys.REFRESH_TOKEN, token)
    }

    override suspend fun updateAccessToken(token: String) {
        dataStoreRepository.save(PreferenceKeys.ACCESS_TOKEN, token)
    }

    override suspend fun clear() {
        dataStoreRepository.clear()
    }
}