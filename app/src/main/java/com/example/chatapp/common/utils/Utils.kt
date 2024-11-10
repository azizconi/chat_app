package com.example.chatapp.common.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.util.Base64
import android.view.ViewTreeObserver
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun <T> MutableState<T>.asState(): State<T> = this

fun getZodiacSignByBirthday(birthday: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(birthday, formatter)

    val day = date.dayOfMonth
    val month = date.monthValue

    return getZodiacSign(day, month)
}

fun getZodiacSign(day: Int, month: Int): String {
    return when (month) {
        1 -> if (day <= 19) "Козерог" else "Водолей"
        2 -> if (day <= 18) "Водолей" else "Рыбы"
        3 -> if (day <= 20) "Рыбы" else "Овен"
        4 -> if (day <= 19) "Овен" else "Телец"
        5 -> if (day <= 20) "Телец" else "Близнецы"
        6 -> if (day <= 20) "Близнецы" else "Рак"
        7 -> if (day <= 22) "Рак" else "Лев"
        8 -> if (day <= 22) "Лев" else "Дева"
        9 -> if (day <= 22) "Дева" else "Весы"
        10 -> if (day <= 22) "Весы" else "Скорпион"
        11 -> if (day <= 21) "Скорпион" else "Стрелец"
        12 -> if (day <= 21) "Стрелец" else "Козерог"
        else -> "Неизвестный месяц"
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: destination.route ?: return koinViewModel()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedGraphViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}

fun Context.datePickerDialog(
    date: String? = null,
    chooseDate: (String) -> Unit,
): DatePickerDialog {

    fun filterDate(number: Int) = if (number in 1..9) "0" else ""

    val calendar = Calendar.getInstance()
    calendar.time = try {
        if (date != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parseDate = dateFormat.parse(date)
            parseDate ?: Date()
        } else {
            Date()
        }
    } catch (e: Exception) {
        Date()
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)



    return DatePickerDialog(
        this, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            val filterDay = filterDate(dayOfMonth)
            val filterMonth = filterDate(month + 1)

            chooseDate("$year-$filterMonth${(month + 1)}-${filterDay}${dayOfMonth}")


        }, year, month, day

    )

}

@SuppressLint("Recycle")
fun Context.uriToBase64(uri: Uri): String? {
    return try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int

        while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }

        val byteArray = outputStream.toByteArray()

        Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun formatMessageDateTime(timestamp: LocalDateTime): String {
    val now = LocalDateTime.now()
    return when {
        timestamp.toLocalDate() == now.toLocalDate() -> timestamp.format(DateTimeFormatter.ofPattern("HH:mm"))
        timestamp.toLocalDate() == now.minusDays(1).toLocalDate() -> "Вчера, ${timestamp.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        else -> timestamp.format(DateTimeFormatter.ofPattern("d MMM, HH:mm"))
    }
}


@Composable
fun rememberImeState(): State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {

            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}