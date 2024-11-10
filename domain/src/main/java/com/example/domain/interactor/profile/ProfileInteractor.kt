package com.example.domain.interactor.profile

data class ProfileInteractor(
    val avatar: String?,
    val avatars: ProfileAvatarsInteractor?,
    val birthday: String?,
    val city: String?,
    val completed_task: Int?,
    val created: String?,
    val id: Int,
    val instagram: String?,
    val last: String?,
    val name: String,
    val online: Boolean,
    val phone: String,
    val status: String?,
    val username: String,
    val vk: String?
)
