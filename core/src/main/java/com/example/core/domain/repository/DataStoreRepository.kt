package com.example.core.domain.repository

import kotlinx.coroutines.flow.Flow

internal interface DataStoreRepository {
    suspend fun <T> save(key: String, value: T)
    fun getString(key: String): Flow<String?>
    suspend fun clear()
}