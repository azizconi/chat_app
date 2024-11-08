package com.example.domain.repository

import com.example.core.Resource
import com.example.domain.interactor.check_auth_code.CheckAuthCodeInteractor
import com.example.domain.interactor.register.RegisterInteractor
import com.example.domain.interactor.send_auth_code.SendAuthCodeInteractor
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun sendAuthCode(phone: String): Flow<Resource<SendAuthCodeInteractor>>
    fun checkAuthCode(phone: String, code: String): Flow<Resource<CheckAuthCodeInteractor>>
    fun register(phone: String, username: String, name: String): Flow<Resource<RegisterInteractor>>
}