package com.example.data.repository

import com.example.domain.interactor.chat.ChatInteractor
import com.example.domain.interactor.chat.MessageInteractor
import com.example.domain.repository.ChatRepository
import java.time.LocalDateTime

class ChatRepositoryImpl: ChatRepository {
    override fun getChats(): List<ChatInteractor> = listOf(
        ChatInteractor(
            id = 12,
            interlocutor = "Иван Иванов",
            photoUrl = "https://img.freepik.com/free-photo/funny-handsome-unshaven-hispanic-man-casual-clothes-with-good-looking-hairstyle-fooling-playing-ape-human-face-expressions_176420-10342.jpg",
            messages = listOf(
                MessageInteractor(id = 171, text = "Привет! Как дела?", isFromUser = false, timestamp = LocalDateTime.now().minusMinutes(10), isRead = true),
                MessageInteractor(id = 194, text = "Привет! Все хорошо, а у тебя?", isFromUser = true, timestamp = LocalDateTime.now().minusMinutes(5), isRead = true),
                MessageInteractor(id = 200, text = "Тоже все отлично!", isFromUser = false, timestamp = LocalDateTime.now().minusMinutes(2), isRead = false)
            )
        ),
        ChatInteractor(
            id = 15,
            interlocutor = "Мария Петрова",
            photoUrl = "https://parrotprint.com/media/wordpress/7630543941b44634748ddea65e5a417c.jpg",
            messages = listOf(
                MessageInteractor(id = 124,text = "Привет, что нового?", isFromUser = false, timestamp = LocalDateTime.now().minusHours(1), isRead = true),
                MessageInteractor(id = 144,text = "Привет, работаю над проектом", isFromUser = true, timestamp = LocalDateTime.now().minusMinutes(45), isRead = true),
                MessageInteractor(id = 154,text = "Звучит здорово! Удачи!", isFromUser = false, timestamp = LocalDateTime.now().minusMinutes(30), isRead = false)
            )
        )
    )

    override fun getChat(id: Int): ChatInteractor? {
        return getChats().find { it.id == id }
    }
}