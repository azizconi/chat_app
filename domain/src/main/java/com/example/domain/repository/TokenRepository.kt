package com.example.domain.repository

import com.example.core.Resource
import com.example.domain.interactor.refresh_token.RefreshTokenInteractor
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getRefreshToken(): Flow<String?>
    fun getAccessToken(): Flow<String?>
    suspend fun updateRefreshToken(token: String?)
    suspend fun updateAccessToken(token: String?)
    suspend fun clear()
}