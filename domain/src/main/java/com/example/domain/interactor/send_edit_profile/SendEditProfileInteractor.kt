package com.example.domain.interactor.send_edit_profile

data class SendEditProfileInteractor(
    val avatar: SendAvatarInteractor?,
    val birthday: String?,
    val city: String?,
    val instagram: String?,
    val name: String,
    val status: String?,
    val username: String,
    val vk: String?
)
