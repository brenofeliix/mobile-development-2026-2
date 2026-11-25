package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(VerdeClaro, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🐾",
                fontSize = 65.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "AdotaPet",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro
        )

        Text(
            text = "Seu novo melhor amigo está aqui!",
            fontSize = 15.sp,
            color = TextoSecundario
        )
    }
}
