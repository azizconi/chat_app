package com.example.data.remote.model.request.check_auth_code

data class CheckAuthCodeRequest(
    val phone: String,
    val code: String
)
