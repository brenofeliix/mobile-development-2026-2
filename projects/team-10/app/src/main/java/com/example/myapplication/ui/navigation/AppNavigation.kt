package com.example.myapplication.ui.navigation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.data.model.AdoptionRequest
import com.example.myapplication.data.model.Pet
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.PetRepository
import com.example.myapplication.ui.screens.AddPetScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.PetDetailScreen
import com.example.myapplication.ui.screens.RegisterScreen
import com.example.myapplication.ui.screens.RequestsScreen
import com.example.myapplication.ui.screens.SplashScreen

@Composable
fun AdotaPetNavigation() {
    val authRepository = remember { AuthRepository() }
    val petRepository = remember { PetRepository() }

    var telaAtual by remember { mutableStateOf("splash") }
    var mensagem by remember { mutableStateOf("") }

    var nomeUsuario by remember { mutableStateOf("") }
    var listaPets by remember { mutableStateOf<List<Pet>>(emptyList()) }
    var favoritos by remember { mutableStateOf<Set<String>>(emptySet()) }
    var solicitacoes by remember { mutableStateOf<List<AdoptionRequest>>(emptyList()) }
    var petSelecionado by remember { mutableStateOf<Pet?>(null) }

    LaunchedEffect(telaAtual) {
        if (telaAtual == "home") {
            petRepository.buscarNomeUsuario { nome -> nomeUsuario = nome }
            petRepository.observarPets { pets -> listaPets = pets }
            petRepository.observarFavoritos { favs -> favoritos = favs }
            petRepository.observarMinhasSolicitacoes { lista -> solicitacoes = lista }
        }
    }

    when (telaAtual) {
        "splash" -> {
            SplashScreen(
                onFinished = {
                    telaAtual = if (authRepository.usuarioEstaLogado()) "home" else "login"
                }
            )
        }

        "login" -> {
            LoginScreen(
                onLogin = { email, senha ->
                    if (email.isBlank() || senha.isBlank()) {
                        mensagem = "Por favor, preencha o e-mail e a senha."
                    } else {
                        authRepository.fazerLogin(
                            email = email,
                            senha = senha,
                            onSuccess = { telaAtual = "home" },
                            onError = { erro -> mensagem = erro }
                        )
                    }
                },
                onRegister = { telaAtual = "register" }
            )
        }

        "register" -> {
            RegisterScreen(
                onRegister = { nome, email, telefone, senha ->
                    if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                        mensagem = "Preencha todos os campos obrigatórios."
                    } else if (senha.length < 6) {
                        mensagem = "A senha deve ter pelo menos 6 caracteres."
                    } else {
                        authRepository.cadastrarUsuario(
                            nome = nome,
                            email = email,
                            senha = senha,
                            telefone = telefone,
                            tipoUsuario = "adotante",
                            onSuccess = {
                                mensagem = "Cadastro realizado com sucesso!"
                                telaAtual = "login"
                            },
                            onError = { erro -> mensagem = erro }
                        )
                    }
                },
                onBack = { telaAtual = "login" }
            )
        }

        "home" -> {
            HomeScreen(
                nomeUsuario = nomeUsuario,
                pets = listaPets,
                favoritos = favoritos,
                onToggleFavorito = { pet ->
                    petRepository.alternarFavorito(pet.id, favoritos.contains(pet.id))
                },
                onSelectPet = { pet ->
                    petSelecionado = pet
                    telaAtual = "detail"
                },
                onAddPet = { telaAtual = "add_pet" },
                onOpenRequests = { telaAtual = "requests" },
                onLogout = {
                    authRepository.sair()
                    nomeUsuario = ""
                    telaAtual = "login"
                }
            )
        }

        "add_pet" -> {
            AddPetScreen(
                onSave = { novoPet ->
                    if (novoPet.nome.isBlank() || novoPet.idade.isBlank()) {
                        mensagem = "Preencha pelo menos o nome e a idade do pet."
                    } else {
                        petRepository.cadastrarPet(
                            pet = novoPet,
                            onSuccess = {
                                mensagem = "${novoPet.nome} foi cadastrado para adoção!"
                                telaAtual = "home"
                            },
                            onError = { erro -> mensagem = erro }
                        )
                    }
                },
                onBack = { telaAtual = "home" }
            )
        }

        "detail" -> {
            petSelecionado?.let { pet ->
                PetDetailScreen(
                    pet = pet,
                    isFavorito = favoritos.contains(pet.id),
                    onToggleFavorito = {
                        petRepository.alternarFavorito(pet.id, favoritos.contains(pet.id))
                    },
                    onAdopt = {
                        petRepository.solicitarAdocao(
                            pet = pet,
                            onSuccess = {
                                mensagem = "Solicitação enviada! Acompanhe na aba Pedidos."
                                telaAtual = "home"
                            },
                            onError = { erro -> mensagem = erro }
                        )
                    },
                    onBack = { telaAtual = "home" }
                )
            }
        }


        "requests" -> {
            RequestsScreen(
                solicitacoes = solicitacoes,
                onBack = { telaAtual = "home" }
            )
        }
    }

    if (mensagem.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { mensagem = "" },
            title = { Text("AdotaPet") },
            text = { Text(mensagem) },
            confirmButton = {
                TextButton(onClick = { mensagem = "" }) {
                    Text("Entendi")
                }
            }
        )
    }
}
