package com.example.domain.interactor.check_auth_code

data class CheckAuthCodeInteractor(
    val access_token: String?,
    val is_user_exists: Boolean,
    val refresh_token: String?,
    val user_id: Int?,
)
