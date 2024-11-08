package com.example.data.remote.model.response.send_auth_code

import com.example.domain.interactor.send_auth_code.SendAuthCodeInteractor
import com.google.gson.annotations.SerializedName

data class SendAuthCodeResponse(
    @SerializedName("is_success") val isSuccess: Boolean
) {
    fun toInteractor() = SendAuthCodeInteractor(isSuccess = isSuccess)
}
