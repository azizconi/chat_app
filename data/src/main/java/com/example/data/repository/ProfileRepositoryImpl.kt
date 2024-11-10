package com.example.data.repository

import com.example.core.Resource
import com.example.core.mapData
import com.example.core.safeApiCall
import com.example.core.toBearer
import com.example.data.local.dao.ProfileDao
import com.example.data.remote.api.AppApi
import com.example.data.remote.model.request.edit_profile.EditProfileRequest
import com.example.domain.interactor.edit_profile.EditProfileInteractor
import com.example.domain.interactor.profile.ProfileInteractor
import com.example.domain.interactor.send_edit_profile.SendEditProfileInteractor
import com.example.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val api: AppApi,
    private val dao: ProfileDao,
) : ProfileRepository {
    override fun getProfile(
        token: String,
        isLocalRequest: Boolean,
    ): Flow<Resource<ProfileInteractor>> = if (isLocalRequest) {
        flow {
            val profile = dao.get().firstOrNull()
            if (profile != null) {
                emit(Resource.Success(profile.toInteractor()))
            } else emit(Resource.Error("Не удалось получить данные. Попробуйте чуть позже"))
        }
    } else {
        safeApiCall {
            api.getProfile(token.toBearer())
        }.map {
            val profile = dao.get().firstOrNull()

            when (it) {
                is Resource.Error -> {
                    if (profile != null) Resource.Success(profile.toInteractor())
                    else Resource.Error(it.message, it.throwable)

                }

                Resource.Idle -> Resource.Idle
                Resource.Loading -> Resource.Loading
                is Resource.Success -> {
                    dao.clear()
                    dao.insert(it.data.toEntity())
                    Resource.Success(dao.get().first().toInteractor())
                }
            }
        }
    }

    override fun editProfile(
        token: String,
        interactor: SendEditProfileInteractor,
    ): Flow<Resource<ProfileInteractor>> = safeApiCall {
        api.editProfile(token.toBearer(), EditProfileRequest.fromInteractor(interactor))
    }.map {

        val profile = dao.get().firstOrNull()

        when (it) {
            is Resource.Error -> Resource.Error(it.message, it.throwable)
            Resource.Idle -> Resource.Idle
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                if (profile != null) {
                    var updatedProfile = profile.copy(
                        birthday = interactor.birthday,
                        city = interactor.city,
                        instagram = interactor.instagram,
                        name = interactor.name,
                        vk = interactor.vk,
                        status = interactor.status
                    )
                    if (it.data.avatars != null) {
                        updatedProfile = updatedProfile.copy(
                            avatar = it.data.avatars!!.avatar,
                            avatars = it.data.toAvatartEntity()
                        )
                    }
                    dao.update(updatedProfile)
                    Resource.Success(updatedProfile.toInteractor())

                } else {
                    Resource.Error("Не удалось обновить. Попробуйте чуть позже", null)
                }
            }
        }


    }
}