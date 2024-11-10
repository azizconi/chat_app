package com.example.data.remote.model.response.profile

import com.example.data.local.entities.profile.ProfileAvatarsEntity
import com.example.domain.interactor.profile.ProfileAvatarsInteractor

data class ProfileAvatars(
    val avatar: String,
    val bigAvatar: String,
    val miniAvatar: String
) {
    fun toInteractor() = ProfileAvatarsInteractor(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar
    )
    fun toEntity() = ProfileAvatarsEntity(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar
    )
}