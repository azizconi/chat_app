package com.example.data.local.entities.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.interactor.profile.ProfileInteractor


@Entity("profile")
data class ProfileEntity(
    val avatar: String?,
    val avatars: ProfileAvatarsEntity?,
    val birthday: String?,
    val city: String?,
    val completed_task: Int?,
    val created: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val instagram: String?,
    val last: String?,
    val name: String,
    val online: Boolean,
    val phone: String,
    val status: String?,
    val username: String,
    val vk: String?
) {
    fun toInteractor() = ProfileInteractor(
        avatar = avatar,
        avatars = avatars?.toInteractor(),
        birthday = birthday,
        city = city,
        completed_task = completed_task,
        created = created,
        id = id,
        instagram = instagram,
        last = last,
        name = name,
        online = online,
        phone = phone,
        status = status,
        username = username,
        vk = vk,
    )
}
