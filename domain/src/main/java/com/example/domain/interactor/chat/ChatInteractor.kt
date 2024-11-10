package com.example.domain.interactor.chat

data class ChatInteractor(
    val id: Int,
    val interlocutor: String,
    val messages: List<MessageInteractor>,
    val photoUrl: String,
)
