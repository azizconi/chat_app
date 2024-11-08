package com.example.data.remote.intercepter

import com.example.core.Constants
import com.example.data.remote.model.response.refresh_token.RefreshTokenResponse
import com.example.domain.repository.TokenRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class AuthInterceptor(
    private val tokenRepository: TokenRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = runBlocking { tokenRepository.getAccessToken().first() }
        val refreshToken = runBlocking { tokenRepository.getAccessToken().first() }
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            response.close()
            synchronized(this) {
                val newAccessToken =
                    runBlocking { tokenRepository.getAccessToken().first() }

                if (accessToken == newAccessToken) {
                    val refreshTokenResponse =
                        refreshAccessToken(refreshToken = refreshToken.toString())


                    if (refreshTokenResponse != null) {
                        runBlocking {
                            tokenRepository.updateAccessToken(refreshTokenResponse.accessToken)
                            tokenRepository.updateRefreshToken(refreshTokenResponse.refreshToken)
                        }
                    } else {
                        runBlocking {
                            tokenRepository.clear()
                        }
                        return response
                    }

                }

                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader(
                        "Authorization",
                        "Bearer ${tokenRepository.getAccessToken()}"
                    )
                    .build()

                return chain.proceed(newRequest)
            }
        }

        return response
    }


    private fun refreshAccessToken(
        refreshToken: String,
    ): RefreshTokenResponse? {
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("refresh_token", refreshToken)
        }

        val requestBody =
            json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("${Constants.BASE_URL}api/v1/users/refresh-token/")
            .post(requestBody)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Gson().fromJson(responseData, RefreshTokenResponse::class.java)
                } else {
                    null
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}