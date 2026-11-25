package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.ui.components.PetTextField
import com.example.myapplication.ui.theme.*


@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("🐶", fontSize = 75.sp)

        Text(
            "Bem-vindo ao AdotaPet!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Encontre um amigo para a vida toda.",
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(35.dp))

        PetTextField(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(15.dp))

        PetTextField(
            value = senha,
            onValueChange = { senha = it },
            label = "Senha",
            password = true
        )

        TextButton(
            onClick = {
                erro = "A recuperação de senha será habilitada com o Firebase."
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Esqueci minha senha", color = VerdeEscuro)
        }

        if (erro.isNotEmpty()) {
            Text(
                text = erro,
                color = Vermelho,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                if (email.isBlank() || senha.isBlank()) {
                    erro = "Preencha o e-mail e a senha."
                } else {
                    erro = ""
                    onLogin(email, senha)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VerdePrincipal
            )
        ) {
            Text(
                "Entrar",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ainda não possui uma conta? ", color = TextoSecundario)

            TextButton(onClick = onRegister) {
                Text(
                    "Cadastre-se",
                    color = VerdeEscuro,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
