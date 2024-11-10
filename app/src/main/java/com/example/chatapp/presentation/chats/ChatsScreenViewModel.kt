package com.example.chatapp.presentation.chats

import androidx.lifecycle.ViewModel
import com.example.domain.use_case.GetChatsUseCase

class ChatsScreenViewModel(private val getChatsUseCase: GetChatsUseCase) : ViewModel() {

    val chats = getChatsUseCase()


}