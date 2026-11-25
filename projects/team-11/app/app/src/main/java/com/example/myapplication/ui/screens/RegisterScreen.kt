package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.PetTextField
import com.example.myapplication.ui.theme.*


@Composable
fun RegisterScreen(
    onRegister: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
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

        Text("🐾", fontSize = 65.sp)

        Text(
            "Criar conta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeEscuro
        )

        Text(
            "Faça parte dessa corrente de amor!",
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(25.dp))

        PetTextField(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome completo"
        )

        Spacer(modifier = Modifier.height(12.dp))

        PetTextField(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(12.dp))

        PetTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = "Telefone",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(12.dp))

        PetTextField(
            value = senha,
            onValueChange = { senha = it },
            label = "Senha",
            password = true
        )

        if (erro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(erro, color = Vermelho)
        }

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = {
                when {
                    nome.isBlank() || email.isBlank() ||
                            telefone.isBlank() || senha.isBlank() -> {
                        erro = "Preencha todos os campos."
                    }

                    !android.util.Patterns.EMAIL_ADDRESS
                        .matcher(email).matches() -> {
                        erro = "Informe um e-mail válido."
                    }

                    senha.length < 6 -> {
                        erro = "A senha deve ter pelo menos 6 caracteres."
                    }

                    else -> {
                        erro = ""
                        onRegister(nome, email, telefone, senha)
                    }
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
                "Criar minha conta",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        TextButton(onClick = onBack) {
            Text("Já tenho uma conta. Fazer login", color = VerdeEscuro)
        }
    }
}
