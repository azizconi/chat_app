package com.example.chatapp.presentation.edit_profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.chatapp.R
import com.example.chatapp.common.components.Avatar
import com.example.chatapp.common.components.DialogProgressBar
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.constants.OutlineButtonHeight
import com.example.chatapp.common.constants.StandardSpaceSize
import com.example.chatapp.common.utils.datePickerDialog
import com.example.chatapp.common.utils.uriToBase64
import com.example.chatapp.presentation.profile.ProfileViewModel
import com.example.core.Constants
import com.example.core.Resource
import com.example.core.Screen
import com.example.domain.interactor.send_edit_profile.SendAvatarInteractor
import com.example.domain.interactor.send_edit_profile.SendEditProfileInteractor
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel,
) {

    val profile by viewModel.profileResult

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.editProfileResult.value) {

        when (val result = viewModel.editProfileResult.value) {
            is Resource.Error -> {
                snackbarHostState.showSnackbar(result.message)
            }

            is Resource.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar("Данные успешно обновлены")
                }
                viewModel.resetEditProfileResult()
            }

            else -> {}
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.edit_profile))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {

            (profile as? Resource.Success)?.data?.let {
                var avatar by remember(it.avatar) {
                    mutableStateOf(
                        if (it.avatars?.avatar != null) Uri.parse(
                            Constants.BASE_URL + it.avatars?.avatar
                        ) else null
                    )
                }
                var city by remember(it.city) { mutableStateOf(it.city ?: "") }
                var instagram by remember(it.instagram) { mutableStateOf(it.instagram ?: "") }
                var vk by remember(it.vk) { mutableStateOf(it.vk ?: "") }
                var birthday by remember(it.birthday) { mutableStateOf(it.birthday ?: "") }
                var status by remember(it.status) { mutableStateOf(it.status ?: "") }

                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.GetContent(),
                    onResult = {
                        avatar = it
                    }
                )

                fun isButtonEnable(): Boolean {
                    return status != it.status || avatar?.path?.contains(it.avatars?.avatar.toString(), true) == false || city != it.city || instagram != it.instagram || vk != it.vk || birthday != it.birthday
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    ) {
                        Avatar(
                            model = avatar,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }

                    VerticalSpacer()

                    Column {
                        Text(text = stringResource(id = R.string.phone))
                        OutlinedTextField(
                            value = it.phone,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true,
                            enabled = false
                        )
                    }
                    VerticalSpacer()
                    Column {
                        Text(text = stringResource(id = R.string.username))
                        OutlinedTextField(
                            value = it.username,
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true,
                            enabled = false
                        )
                    }
                    VerticalSpacer()

                    Column {

                        Text(text = stringResource(id = R.string.about_myself))
                        OutlinedTextField(
                            value = status,
                            onValueChange = { status = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true,
                        )
                    }
                    VerticalSpacer()
                    Column {

                        Text(text = stringResource(id = R.string.city))
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true,
                        )
                    }
                    VerticalSpacer()
                    Column {

                        Text(text = "Instagram")
                        OutlinedTextField(
                            value = instagram,
                            onValueChange = { instagram = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true,
                        )
                    }
                    VerticalSpacer()
                    Column {

                        Text(text = "VK")
                        OutlinedTextField(
                            value = vk,
                            onValueChange = { vk = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true,
                        )

                    }
                    VerticalSpacer()

                    Column {
                        Text(text = stringResource(id = R.string.birthday))
                        OutlinedTextField(
                            value = birthday,
                            onValueChange = { birthday = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    context
                                        .datePickerDialog(birthday) {
                                            birthday = it
                                        }
                                        .show()
                                },
                            singleLine = true,
                            enabled = false
                        )
                    }
                    VerticalSpacer()

                    Button(
                        onClick = {
                            val interactor = SendEditProfileInteractor(
                                avatar = if (avatar != null) {
                                    context.uriToBase64(avatar!!)?.let {
                                        SendAvatarInteractor(
                                            base_64 = it,
                                            filename = UUID.randomUUID().toString()
                                        )
                                    }
                                } else null,
                                birthday = if (birthday != it.birthday) birthday else it.birthday,
                                city = if (city != it.city) city else it.city,
                                instagram = if (instagram != it.instagram) instagram else it.instagram,
                                name = it.name,
                                status = if (status != it.status) status else it.status,
                                username = it.username,
                                vk = if (vk != it.vk) vk else it.vk
                            )
                            viewModel.editProfile(interactor)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(OutlineButtonHeight),
                        enabled = isButtonEnable()
                    ) {
                        Text(text = "Изменить")
                    }
                    VerticalSpacer()
                }


            }

        }

    }

    DialogProgressBar(result = viewModel.editProfileResult.value)

}

