package com.example.myapplication.data.model

data class Pet(
    val id: String = "",
    val nome: String = "",
    val especie: String = "",
    val idade: String = "",
    val porte: String = "",
    val localidade: String = "",
    val descricao: String = "",
    val emoji: String = "🐶",
    val fotoBase64: String = "",
    val fotos: List<String> = emptyList(),
    val disponivel: Boolean = true,
    val donoId: String = ""
) {
    // Retorna todas as fotos cadastradas (incluindo a foto antiga caso exista)
    fun todasAsFotos(): List<String> {
        return fotos.ifEmpty {
            if (fotoBase64.isNotBlank()) listOf(fotoBase64) else emptyList()
        }
    }
}
