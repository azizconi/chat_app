package com.example.data.remote.model.request.edit_profile

import com.example.domain.interactor.send_edit_profile.SendEditProfileInteractor

data class EditProfileRequest(
    val avatar: Avatar?,
    val birthday: String?,
    val city: String?,
    val instagram: String?,
    val name: String?,
    val status: String?,
    val username: String,
    val vk: String?,
) {
    companion object {
        fun fromInteractor(interactor: SendEditProfileInteractor): EditProfileRequest {
            return EditProfileRequest(
                avatar = Avatar.fromInteractor(interactor.avatar),
                birthday = interactor.birthday,
                city = interactor.city,
                instagram = interactor.instagram,
                name = interactor.name,
                status = interactor.status,
                username = interactor.username,
                vk = interactor.vk,
            )
        }
    }
}