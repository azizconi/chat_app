package com.example.data.remote.model.response.profile

import com.example.data.local.entities.profile.ProfileEntity
import com.example.domain.interactor.profile.ProfileInteractor

data class ProfileResponse(
    val profile_data: ProfileData,
) {
    fun toInteractor() = ProfileInteractor(
        avatar = profile_data.avatar,
        avatars = profile_data.avatars?.toInteractor(),
        birthday = profile_data.birthday,
        city = profile_data.city,
        completed_task = profile_data.completed_task,
        created = profile_data.created,
        id = profile_data.id,
        instagram = profile_data.instagram,
        last = profile_data.last,
        name = profile_data.name,
        online = profile_data.online,
        phone = profile_data.phone,
        status = profile_data.status,
        username = profile_data.username,
        vk = profile_data.vk,
    )

    fun toEntity() = ProfileEntity(
        avatar = profile_data.avatar,
        avatars = profile_data.avatars?.toEntity(),
        birthday = profile_data.birthday,
        city = profile_data.city,
        completed_task = profile_data.completed_task,
        created = profile_data.created,
        id = profile_data.id,
        instagram = profile_data.instagram,
        last = profile_data.last,
        name = profile_data.name,
        online = profile_data.online,
        phone = profile_data.phone,
        status = profile_data.status,
        username = profile_data.username,
        vk = profile_data.vk,
    )
}