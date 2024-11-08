package com.example.chatapp.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.common.components.DialogProgressBar
import com.example.chatapp.common.components.VerticalSpacer
import com.example.chatapp.common.constants.OutlineButtonHeight
import com.example.chatapp.common.constants.StandardSpaceSize
import com.example.core.Resource
import com.example.core.Screen
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthScreenViewModel = koinViewModel(),
) {

    var phone by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }

    val sendAuthCodeResult by viewModel.sendAuthCodeResult

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    LaunchedEffect(sendAuthCodeResult) {
        when(sendAuthCodeResult) {

            is Resource.Success -> {
                if ((sendAuthCodeResult as Resource.Success).data.isSuccess) {
                    navController.navigate(Screen.OtpScreen(phone))
                }
                viewModel.resetSendAuthCodeResult()
            }
            is Resource.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar((sendAuthCodeResult as Resource.Error).message)
                    viewModel.resetSendAuthCodeResult()
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.auth),
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            PhoneNumberInput(
                onNumberChanged = { number ->
                    phone = number
                },
                onCountryChanged = { countryCode ->
                    code = countryCode
                },
                onPhoneValid = {
                    isValid = it
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (isValid) {
                        viewModel.sendAuthCode(code + phone)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OutlineButtonHeight)
                    .padding(horizontal = StandardSpaceSize),
                enabled = isValid
            ) {
                Text("Войти")
            }
            VerticalSpacer()


        }
    }

    DialogProgressBar(result = sendAuthCodeResult)

}

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    onNumberChanged: (String) -> Unit = {},
    onCountryChanged: (String) -> Unit = {},
    onPhoneValid: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current
    val phoneUtil = remember { PhoneNumberUtil.getInstance() }
    var countryCode by remember { mutableStateOf("+7") }
    var regionCode by remember { mutableStateOf("RU") }
    var phoneNumber by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    var isValid by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val defaultRegion = getCurrentRegion()
        regionCode = defaultRegion
        val countryCodeInt = phoneUtil.getCountryCodeForRegion(regionCode)
        countryCode = "+$countryCodeInt"

        onNumberChanged(phoneNumber)
        onCountryChanged(countryCode)
        onPhoneValid(false)
    }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Выбор флага и кода страны
            Column(
                modifier = Modifier
                    .clickable { isDialogOpen = true }
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = getFlagEmoji(regionCode))
                Text(text = countryCode)
            }

            // Поле ввода номера телефона
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { input ->
                    phoneNumber = input
                    onNumberChanged(input)

                    // Проверка валидности номера
                    isValid = isValidPhoneNumber(input, regionCode)
                    onPhoneValid(isValid)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = "Введите номер телефона") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                isError = !isValid
            )
        }

        // Отображение ошибки, если номер не валиден
        if (!isValid && phoneNumber.isNotBlank()) {
            Text(
                text = "Номер телефона некорректен",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }

    // Диалог выбора страны
    CountryPickerDialog(
        isOpen = isDialogOpen,
        onDismiss = { isDialogOpen = false },
        onCountrySelected = { selectedRegion, selectedCode ->
            regionCode = selectedRegion
            countryCode = selectedCode
            phoneNumber = "" // Очистка текущего номера при смене страны
            isValid = true // Сброс состояния валидности
            onCountryChanged(selectedCode)
        }
    )
}

@Composable
fun CountryPickerDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onCountrySelected: (String, String) -> Unit,
) {
    val phoneUtil = PhoneNumberUtil.getInstance()
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Выберите страну") },
            text = {
                // Пример списка стран. Для полного списка используйте phoneUtil.supportedRegions
                val regions = listOf("RU", "US", "GB", "FR", "DE", "CN", "JP", "IN", "TJ")
                Column {
                    regions.forEach { countryCode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCountrySelected(
                                        countryCode,
                                        "+${phoneUtil.getCountryCodeForRegion(countryCode)}"
                                    )
                                    onDismiss()
                                }
                                .padding(8.dp)
                        ) {
                            Text(text = getFlagEmoji(countryCode))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$countryCode (+${
                                    phoneUtil.getCountryCodeForRegion(
                                        countryCode
                                    )
                                })"
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Закрыть")
                }
            }
        )
    }
}


fun getFlagEmoji(regionCode: String): String {
    return regionCode.uppercase(Locale.ROOT).map {
        0x1F1E6 - 'A'.code + it.code
    }.joinToString("") { String(Character.toChars(it)) }
}

fun getCurrentRegion(): String {
    val locale = Locale("ru", "RU")
    return PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(
        PhoneNumberUtil.getInstance().getCountryCodeForRegion(locale.country)
    )
}

fun isValidPhoneNumber(phoneNumber: String, regionCode: String): Boolean {
    val phoneUtil = PhoneNumberUtil.getInstance()
    return try {
        val numberProto = phoneUtil.parse(phoneNumber, regionCode)
        phoneUtil.isValidNumber(numberProto)
    } catch (e: NumberParseException) {
        false
    }
}