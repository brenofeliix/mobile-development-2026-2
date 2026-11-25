package com.example.myapplication.data.model

data class AdoptionRequest(
    val id: String = "",
    val petId: String = "",
    val petNome: String = "",
    val petEmoji: String = "🐾",
    val adotanteId: String = "",
    val status: String = "pendente"
)
