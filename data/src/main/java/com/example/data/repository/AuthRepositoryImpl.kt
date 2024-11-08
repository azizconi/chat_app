package com.example.data.repository

import com.example.core.Resource
import com.example.core.mapData
import com.example.core.safeApiCall
import com.example.data.remote.api.AppApi
import com.example.data.remote.api.AuthApi
import com.example.data.remote.model.request.check_auth_code.CheckAuthCodeRequest
import com.example.data.remote.model.request.register.RegisterRequest
import com.example.data.remote.model.request.send_auth_code.SendAuthCodeRequest
import com.example.domain.interactor.check_auth_code.CheckAuthCodeInteractor
import com.example.domain.interactor.register.RegisterInteractor
import com.example.domain.interactor.send_auth_code.SendAuthCodeInteractor
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val api: AuthApi): AuthRepository {
    override fun sendAuthCode(
        phone: String,
    ): Flow<Resource<SendAuthCodeInteractor>> = safeApiCall {
        api.sendAuthCode(request = SendAuthCodeRequest(phone))
    }.mapData { it.toInteractor() }

    override fun checkAuthCode(
        phone: String,
        code: String,
    ): Flow<Resource<CheckAuthCodeInteractor>> = safeApiCall {
        api.checkAuthCode(CheckAuthCodeRequest(phone = phone, code = code))
    }.mapData { it.toInteractor() }

    override fun register(
        phone: String,
        username: String,
        name: String,
    ): Flow<Resource<RegisterInteractor>> = safeApiCall {
        api.register(RegisterRequest(phone = phone, username = username, name = name))
    }.mapData { it.toInteractor() }
}