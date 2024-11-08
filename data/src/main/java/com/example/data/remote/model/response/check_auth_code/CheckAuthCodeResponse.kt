package com.example.data.remote.model.response.check_auth_code

import com.example.domain.interactor.check_auth_code.CheckAuthCodeInteractor

data class CheckAuthCodeResponse(
    val access_token: String,
    val is_user_exists: Boolean,
    val refresh_token: String,
    val user_id: Int,
) {
    fun toInteractor() = CheckAuthCodeInteractor(
        access_token = access_token,
        is_user_exists = is_user_exists,
        refresh_token = refresh_token,
        user_id = user_id,
    )
}