package com.example.diana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diana.ui.theme.DianaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DianaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GuessNumberGame()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessNumberGame() {
    val secretNumber = 67
    var userInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Попробуйте угадать число (1–100)") }
    var gameFinished by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4E1))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Угадай число🌸",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD81B60),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = message,
            fontSize = 18.sp,
            color = Color(0xFFAD1457),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = userInput,
            onValueChange = { newValue ->
                userInput = newValue.filter { it.isDigit() }
            },
            label = { Text("Введите число") },
            singleLine = true,
            enabled = !gameFinished,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val number = userInput.toIntOrNull()
                if (number == null) {
                    message = "Введите корректное число!"
                    return@Button
                }

                when {
                    number < secretNumber -> {
                        message = "Недолёт 🚫"
                        gameFinished = true
                    }
                    number > secretNumber -> {
                        message = "Перелёт 🚫"
                        gameFinished = true
                    }
                    else -> {
                        message = "В точку! 🎯"
                        gameFinished = true
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFB6C1),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !gameFinished
        ) {
            Text(text = "ПРОВЕРИТЬ", fontSize = 16.sp)
        }

        if (gameFinished) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    userInput = ""
                    message = "Попробуйте угадать число (1–100)"
                    gameFinished = false
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC0CB),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "СЫГРАТЬ ЕЩЁ РАЗ", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGuessNumberGame() {
    DianaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GuessNumberGame()
        }
    }
}

