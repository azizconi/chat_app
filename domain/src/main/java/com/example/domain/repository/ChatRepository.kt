package com.example.domain.repository

import com.example.domain.interactor.chat.ChatInteractor

interface ChatRepository {
    fun getChats(): List<ChatInteractor>
    fun getChat(id: Int): ChatInteractor?
}