package com.example.domain.use_case

import com.example.domain.repository.ChatRepository

class GetChatUseCase(private val repository: ChatRepository) {
    operator fun invoke(id: Int) = repository.getChat(id)
}