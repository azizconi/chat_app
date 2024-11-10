package com.example.chatapp.di

import com.example.chatapp.common.viewModels.MainViewModel
import com.example.chatapp.presentation.auth.AuthScreenViewModel
import com.example.chatapp.presentation.chat.ChatScreenViewModel
import com.example.chatapp.presentation.chats.ChatsScreenViewModel
import com.example.chatapp.presentation.otp.OtpScreenViewModel
import com.example.chatapp.presentation.profile.ProfileViewModel
import com.example.chatapp.presentation.register.RegisterScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module



val viewModelsModule: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { AuthScreenViewModel(get()) }
    viewModel { OtpScreenViewModel(get()) }
    viewModel { RegisterScreenViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ChatsScreenViewModel(get()) }
    viewModel { ChatScreenViewModel(get()) }
}