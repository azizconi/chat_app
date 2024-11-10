package com.example.domain.use_case

import com.example.domain.repository.ChatRepository

class GetChatsUseCase(private val repository: ChatRepository) {
    operator fun invoke() = repository.getChats()
}