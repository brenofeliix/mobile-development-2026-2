package com.example.myapplication.data.model

data class User(
    val id: String = "",
    val nome: String = "",
    val email: String = "",
    val telefone: String = "",
    val tipoUsuario: String = "adotante",
    val fotoPerfil: String = ""
)