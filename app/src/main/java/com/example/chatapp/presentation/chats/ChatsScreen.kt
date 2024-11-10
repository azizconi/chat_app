package com.example.chatapp.presentation.chats

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.common.components.Avatar
import com.example.chatapp.common.components.HorizontalSpacer
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.utils.formatMessageDateTime
import com.example.core.Screen
import com.example.domain.interactor.chat.ChatInteractor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    navController: NavHostController,
    viewModel: ChatsScreenViewModel = koinViewModel(),
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.chats))
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

            LazyColumn {
                items(viewModel.chats) {
                    ChatItem(it) {
                        navController.navigate(Screen.ChatScreen(it.id))
                    }
                }
            }

        }
    }

}

@Composable
fun ChatItem(
    chat: ChatInteractor,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Avatar(
                model = chat.photoUrl,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            HorizontalSpacer()
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.interlocutor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(4.dp)
                    val unreadMessages = chat.messages.filter { !it.isRead && !it.isFromUser }.size
                    if (unreadMessages > 0) {
                        Badge {
                            Text(text = unreadMessages.toString())
                        }
                    }
                }
                VerticalSpacer(8.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val message = chat.messages.last()
                    Text(text = message.text, modifier = Modifier.weight(1f), maxLines = 1)
                    HorizontalSpacer(4.dp)
                    Text(text = formatMessageDateTime(message.timestamp))
                }
            }
        }
    }
}

