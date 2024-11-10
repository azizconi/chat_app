package com.example.data.remote.api

import com.example.data.remote.model.request.edit_profile.EditProfileRequest
import com.example.data.remote.model.response.edit_profile.EditProfileResponse
import com.example.data.remote.model.response.profile.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface AppApi {

    @GET("api/v1/users/me/")
    suspend fun getProfile(
        @Header("Authorization") accessToken: String
    ): Response<ProfileResponse>

    @PUT("api/v1/users/me/")
    suspend fun editProfile(
        @Header("Authorization") accessToken: String,
        @Body request: EditProfileRequest
    ): Response<EditProfileResponse>
}