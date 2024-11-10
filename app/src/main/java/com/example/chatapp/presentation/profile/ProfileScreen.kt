package com.example.chatapp.presentation.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.chatapp.R
import com.example.chatapp.common.components.Avatar
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.utils.getZodiacSignByBirthday
import com.example.core.Constants.BASE_URL
import com.example.core.Resource
import com.example.core.Screen
import com.example.domain.interactor.profile.ProfileInteractor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel,
) {


    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }


    val profile by viewModel.profileResult

    LaunchedEffect(profile) {
        isRefreshing = profile is Resource.Loading
        when (profile) {
            is Resource.Error -> viewModel.getProfile(false)
            Resource.Idle -> viewModel.getProfile(false)
            is Resource.Success -> viewModel.getProfile(true)
            else -> {}
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.profile))
                },
                actions = {
                    if (profile is Resource.Success) {
                        IconButton(onClick = { navController.navigate(Screen.EditProfileScreen) }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                    }
                }
            )

        }
    ) {

        PullToRefreshBox(
            state = pullRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.getProfile(false)
                isRefreshing = true
            },
            modifier = Modifier.padding(it)
        ) {
            Column(modifier = Modifier) {
                when (profile) {
                    is Resource.Error -> {}
                    Resource.Idle -> {}
                    Resource.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is Resource.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val profileData = (profile as Resource.Success<ProfileInteractor>).data

                            val avatar = profileData.avatars?.avatar
                            val phone = profileData.phone
                            val username = profileData.username
                            val birthday = profileData.birthday
                            val about = profileData.status ?: "Не указано"

                            Avatar(
                                model = BASE_URL + avatar,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )


                            VerticalSpacer()

                            Column(modifier = Modifier.fillMaxWidth()) {

                                UserInfoItem(
                                    title = stringResource(id = R.string.phone),
                                    text = phone
                                )
                                VerticalSpacer()
                                UserInfoItem(
                                    title = stringResource(id = R.string.username),
                                    text = username
                                )
                                VerticalSpacer()

                                UserInfoItem(
                                    title = stringResource(id = R.string.zodiac_sign),
                                    text = birthday?.let { it1 -> getZodiacSignByBirthday(it1) }
                                        ?: "День рождения не указан"
                                )
                                VerticalSpacer()
                                UserInfoItem(
                                    title = stringResource(id = R.string.about_myself),
                                    text = about
                                )


                            }


                        }

                    }
                }


            }
        }
    }


}

@Composable
fun UserInfoItem(
    title: String,
    text: String,
) {

    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge
    )

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )


}

