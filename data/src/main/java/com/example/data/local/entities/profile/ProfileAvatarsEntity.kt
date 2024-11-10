package com.example.data.local.entities.profile

import com.example.domain.interactor.profile.ProfileAvatarsInteractor

data class ProfileAvatarsEntity(
    val avatar: String,
    val bigAvatar: String,
    val miniAvatar: String
) {
    fun toInteractor() = ProfileAvatarsInteractor(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar
    )
}
