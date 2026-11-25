package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Pet
import com.example.myapplication.ui.components.PetImage
import com.example.myapplication.ui.theme.*

@Composable
fun HomeScreen(
    nomeUsuario: String,
    pets: List<Pet>,
    favoritos: Set<String>,
    onToggleFavorito: (Pet) -> Unit,
    onSelectPet: (Pet) -> Unit,
    onAddPet: () -> Unit,
    onOpenRequests: () -> Unit,
    onLogout: () -> Unit
) {
    var filtroAtual by remember { mutableStateOf("Todos") }

    val petsFiltrados = remember(pets, favoritos, filtroAtual) {
        when (filtroAtual) {
            "Cachorros" -> pets.filter { it.especie.equals("Cachorro", ignoreCase = true) }
            "Gatos" -> pets.filter { it.especie.equals("Gato", ignoreCase = true) }
            "Favoritos" -> pets.filter { favoritos.contains(it.id) }
            else -> pets
        }
    }

    Scaffold(
        containerColor = Fundo,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPet,
                containerColor = VerdePrincipal,
                contentColor = Branco
            ) {
                Icon(Icons.Default.Add, contentDescription = "Cadastrar Pet")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (nomeUsuario.isNotBlank()) "Olá, $nomeUsuario! 🐾" else "Olá! 🐾",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeEscuro
                    )
                    Text(text = "Vamos encontrar seu novo amigo?", fontSize = 14.sp, color = TextoSecundario)
                }
                Row {
                    TextButton(onClick = onOpenRequests) {
                        Text("Pedidos", color = VerdePrincipal, fontWeight = FontWeight.Bold)
                    }
                    TextButton(onClick = onLogout) {
                        Text("Sair", color = TextoSecundario)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Todos", "Cachorros", "Gatos", "Favoritos").forEach { categoria ->
                    FilterChip(
                        selected = filtroAtual == categoria,
                        onClick = { filtroAtual = categoria },
                        label = { Text(categoria) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (petsFiltrados.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Nenhum pet encontrado neste filtro.", color = TextoSecundario)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(petsFiltrados) { pet ->
                        val isFav = favoritos.contains(pet.id)
                        val fotoCapa = pet.todasAsFotos().firstOrNull() ?: ""
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable { onSelectPet(pet) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Branco),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                PetImage(
                                    fotoBase64 = fotoCapa,
                                    emoji = pet.emoji,
                                    emojiSize = 32.sp,
                                    modifier = Modifier.size(68.dp).clip(RoundedCornerShape(14.dp))
                                )

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(pet.nome, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextoPrincipal)
                                    Text("${pet.especie} • ${pet.idade}", fontSize = 13.sp, color = TextoSecundario)
                                    if (pet.localidade.isNotBlank()) {
                                        Text("📍 ${pet.localidade}", fontSize = 12.sp, color = TextoSecundario)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("Disponível para adoção", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = VerdePrincipal)
                                }

                                IconButton(onClick = { onToggleFavorito(pet) }) {
                                    Icon(
                                        imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favoritar",
                                        tint = if (isFav) Vermelho else TextoSecundario
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
