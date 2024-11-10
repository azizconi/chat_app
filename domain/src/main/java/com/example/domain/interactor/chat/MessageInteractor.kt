package com.example.domain.interactor.chat

import java.time.LocalDateTime

data class MessageInteractor(
    val id: Int,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: LocalDateTime,
    val isRead: Boolean,
)
