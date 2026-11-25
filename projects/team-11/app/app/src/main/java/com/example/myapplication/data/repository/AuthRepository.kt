package com.example.myapplication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        telefone: String,
        tipoUsuario: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email.trim(), senha)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: ""
                val usuarioMap = hashMapOf(
                    "id" to uid,
                    "nome" to nome,
                    "email" to email.trim(),
                    "telefone" to telefone,
                    "tipoUsuario" to tipoUsuario,
                    "fotoPerfil" to ""
                )

                db.collection("users").document(uid)
                    .set(usuarioMap)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e ->
                        onError(e.localizedMessage ?: "Erro ao salvar dados no banco.")
                    }
            }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Erro ao criar conta.")
            }
    }

    fun fazerLogin(
        email: String,
        senha: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email.trim(), senha)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "E-mail ou senha inválidos.")
            }
    }

    fun usuarioEstaLogado(): Boolean = auth.currentUser != null

    fun sair() {
        auth.signOut()
    }
}
