package com.example.data.repository

import com.example.core.PreferenceKeys
import com.example.domain.repository.TokenRepository
import com.example.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class TokenRepositoryImpl(
    private val dataStoreRepository: DataStoreRepository,
) : TokenRepository {
    override fun getRefreshToken(): Flow<String?> =
        dataStoreRepository.getString(PreferenceKeys.REFRESH_TOKEN)

    override fun getAccessToken(): Flow<String?> =
        dataStoreRepository.getString(PreferenceKeys.ACCESS_TOKEN)


    override suspend fun updateRefreshToken(token: String?) {
        dataStoreRepository.save(PreferenceKeys.REFRESH_TOKEN, token)
    }

    override suspend fun updateAccessToken(token: String?) {
        dataStoreRepository.save(PreferenceKeys.ACCESS_TOKEN, token)
    }

    override suspend fun clear() {
        dataStoreRepository.clear()
    }
}