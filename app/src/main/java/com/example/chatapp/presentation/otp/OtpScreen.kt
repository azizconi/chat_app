package com.example.chatapp.presentation.otp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.common.components.DialogProgressBar
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.constants.OutlineButtonHeight
import com.example.chatapp.common.constants.StandardSpaceSize
import com.example.chatapp.presentation.auth.isValidPhoneNumber
import com.example.core.Resource
import com.example.core.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    navController: NavHostController,
    phone: String,
    viewModel: OtpScreenViewModel = koinViewModel(),
) {

    var otpCode by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()



    LaunchedEffect(viewModel.otpResult.value) {
        when(val result = viewModel.otpResult.value) {
            is Resource.Error -> {
                scope.launch {
                    if (result.throwable is IOException) {
                        snackbarHostState.showSnackbar(result.message)
                    } else {
                        snackbarHostState.showSnackbar("Введённый вами код был неверным")
                    }

                    viewModel.resetCheckAuthCodeResult()
                }
            }

            is Resource.Success -> {
                if (!result.data.is_user_exists) {
                    navController.navigate(Screen.RegisterScreen(phone = phone)) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }
            }
            else -> {}
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.confirm_otp))
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
        Column(modifier = Modifier.padding(it)) {

            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = otpCode,
                    onValueChange = { input ->
                        if (input.length <= 6) {
                            val value = input.trim().filter { it.isDigit() }
                            otpCode = value
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text(text = "Введите код подтверждения") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.checkAuthCode(phone, otpCode)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OutlineButtonHeight)
                    .padding(horizontal = StandardSpaceSize),
                enabled = otpCode.length == 6
            ) {
                Text("Войти")
            }
            VerticalSpacer()


        }
    }
    DialogProgressBar(result = viewModel.otpResult.value)


}