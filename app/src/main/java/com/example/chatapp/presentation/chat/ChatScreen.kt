package com.example.chatapp.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.utils.formatMessageDateTime
import com.example.chatapp.common.utils.rememberImeState
import com.example.domain.interactor.chat.MessageInteractor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavHostController,
    chatId: Int,
    viewModel: ChatScreenViewModel = koinViewModel(),
) {

    val chat = viewModel.getChat(chatId)

    val isKeyboardVisible by rememberImeState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = chat?.interlocutor ?: stringResource(id = R.string.chat))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                reverseLayout = true
            ) {
                items(chat?.messages?.reversed() ?: emptyList()) { message ->
                    MessageItem(message = message)
                }
            }

            Column {
                ChatInput(
                    onMessageSent = { text ->

                    }
                )
                if (isKeyboardVisible) {
                    VerticalSpacer(24.dp)
                }
            }
        }

    }


}

@Composable
fun ChatInput(onMessageSent: (String) -> Unit) {
    val messageText = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = messageText.value,
            onValueChange = { messageText.value = it },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(12.dp),
            textStyle = TextStyle(color = Color.Black)
        )
        VerticalSpacer()

        IconButton(
            onClick = {
                onMessageSent(messageText.value)
                messageText.value = ""
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
                tint = if (messageText.value.isNotEmpty()) Color.Black else Color.LightGray
            )
        }

    }
}

@Composable
fun MessageItem(message: MessageInteractor) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier.padding(vertical = 4.dp),
            shape = MaterialTheme.shapes.medium,
            color = if (message.isFromUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = message.text,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                VerticalSpacer(4.dp)
                Text(
                    text = "Отправлено: ${formatMessageDateTime(message.timestamp)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )
                if (message.isRead) {
                    VerticalSpacer(4.dp)
                    Text(
                        text = "Прочитано",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}