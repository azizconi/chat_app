package com.example.core.data.remote.intercepter

import com.example.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(
    private val authRepository: AuthRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = runBlocking { authRepository.getAccessToken().first() }
        val refreshToken = runBlocking { authRepository.getAccessToken().first() }
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            synchronized(this) {
                val newAccessToken = runBlocking { authRepository.getAccessToken().first() }

                if (accessToken == newAccessToken) {
                    val refreshTokenResponse =
                        runBlocking { authRepository.refreshAccessToken(refreshToken!!) }

                    if (refreshTokenResponse.isSuccessful) {
                        refreshTokenResponse.body()?.let {
                            runBlocking { authRepository.updateAccessToken(it.accessToken) }
                            runBlocking { authRepository.updateRefreshToken(it.refreshToken) }
                        }
                    } else {
                        runBlocking { authRepository.clear() }
                        return response
                    }
                }

                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${authRepository.getAccessToken()}")
                    .build()

                return chain.proceed(newRequest)
            }
        }

        return response
    }
}