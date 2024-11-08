package com.example.core

import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable
    data object ChatsScreen : Screen()

    @Serializable
    data object ChatScreen : Screen()

    @Serializable
    data object ProfileScreen : Screen()

    @Serializable
    data object EditProfileScreen : Screen()

    @Serializable
    data object AuthScreen : Screen()

    @Serializable
    data class RegisterScreen(val phone: String) : Screen()

    @Serializable
    data class OtpScreen(val phone: String) : Screen()

    @Serializable
    data object EmptyScreen : Screen()

}