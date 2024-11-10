package com.example.chatapp.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.common.components.DialogProgressBar
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.constants.OutlineButtonHeight
import com.example.chatapp.common.constants.StandardSpaceSize
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    phone: String,
    viewModel: RegisterScreenViewModel = koinViewModel(),
) {

    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.register))
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
            modifier = Modifier.padding(it)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = phone,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    enabled = false
                )

                VerticalSpacer()

                UsernameInputField(
                    username = username,
                    onUsernameChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                VerticalSpacer()
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Имя") },
                    singleLine = true,
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.register(phone, username, name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OutlineButtonHeight)
                    .padding(horizontal = StandardSpaceSize),
                enabled = username.isNotEmpty() && name.isNotEmpty()
            ) {
                Text("Войти")
            }
            VerticalSpacer()


        }
    }

    DialogProgressBar(result = viewModel.registerResult.value)
}


@Composable
fun UsernameInputField(
    username: String,
    onUsernameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val usernameRegex = "^[A-Za-z0-9-_]*$".toRegex()

    OutlinedTextField(
        value = username,
        onValueChange = { newValue ->
            if (newValue.matches(usernameRegex)) {
                onUsernameChange(newValue)
            }
        },
        label = { Text("Имя пользователя") },
        singleLine = true,
        modifier = modifier
    )
}