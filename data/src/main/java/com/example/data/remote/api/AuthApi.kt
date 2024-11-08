package com.example.data.remote.api

import com.example.data.remote.model.request.check_auth_code.CheckAuthCodeRequest
import com.example.data.remote.model.request.refresh_token.RefreshTokenRequest
import com.example.data.remote.model.request.register.RegisterRequest
import com.example.data.remote.model.request.send_auth_code.SendAuthCodeRequest
import com.example.data.remote.model.response.check_auth_code.CheckAuthCodeResponse
import com.example.data.remote.model.response.refresh_token.RefreshTokenResponse
import com.example.data.remote.model.response.register.RegisterResponse
import com.example.data.remote.model.response.send_auth_code.SendAuthCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {


    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: SendAuthCodeRequest): Response<SendAuthCodeResponse>

    @POST("api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequest): Response<CheckAuthCodeResponse>

    @POST("api/v1/users/register/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

}