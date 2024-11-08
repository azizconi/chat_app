package com.example.domain.use_case

import com.example.domain.repository.DataStoreRepository
import com.example.core.PreferenceKeys

class GetAccessTokenUseCase(private val repository: DataStoreRepository) {
    operator fun invoke() = repository.getString(PreferenceKeys.ACCESS_TOKEN)
}