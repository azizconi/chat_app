package com.example.chatapp.presentation.chat

import androidx.lifecycle.ViewModel
import com.example.domain.use_case.GetChatUseCase

class ChatScreenViewModel(private val getChatUseCase: GetChatUseCase): ViewModel() {

    fun getChat(id: Int) = getChatUseCase.invoke(id)

}