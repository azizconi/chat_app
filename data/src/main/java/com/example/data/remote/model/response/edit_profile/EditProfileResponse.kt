package com.example.data.remote.model.response.edit_profile

import com.example.data.local.entities.profile.ProfileAvatarsEntity
import com.example.domain.interactor.edit_profile.EditProfileInteractor
import com.example.domain.interactor.profile.ProfileAvatarsInteractor

data class EditProfileResponse(
    val avatars: Avatars?
) {
    fun toInteractor() = EditProfileInteractor (
        avatar = avatars?.avatar,
        bigAvatar = avatars?.bigAvatar,
        miniAvatar = avatars?.miniAvatar,
    )
    fun toAvatartEntity(): ProfileAvatarsEntity? = avatars?.let {
        ProfileAvatarsEntity(
        avatar = it.avatar,
        bigAvatar = it.bigAvatar,
        miniAvatar = it.miniAvatar,
    )
    }

}