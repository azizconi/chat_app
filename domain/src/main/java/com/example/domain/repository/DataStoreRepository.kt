package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> save(key: String, value: T)
    fun getString(key: String): Flow<String?>
    suspend fun clear()
}