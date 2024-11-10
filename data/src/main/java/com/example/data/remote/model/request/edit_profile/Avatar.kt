package com.example.data.remote.model.request.edit_profile

import com.example.domain.interactor.send_edit_profile.SendAvatarInteractor

data class Avatar(
    val base_64: String,
    val filename: String
) {
    companion object {
        fun fromInteractor(
            avatar: SendAvatarInteractor?
        ): Avatar? = if (avatar != null)Avatar(
            base_64 = avatar.base_64,
            filename = avatar.filename
        ) else null
    }
}