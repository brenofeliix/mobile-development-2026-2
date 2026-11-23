
package com.example.myapplication.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Pet
import com.example.myapplication.data.repository.PetRepository
import com.example.myapplication.ui.components.PetImage
import com.example.myapplication.ui.theme.*

@Composable
fun PetDetailScreen(
    pet: Pet,
    isFavorito: Boolean,
    onToggleFavorito: () -> Unit,
    onAdopt: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val petRepository = remember { PetRepository() }
    val fotos = pet.todasAsFotos()

    var nomeDono by remember(pet.donoId) {
        mutableStateOf("Carregando...")
    }

    var telefoneDono by remember(pet.donoId) {
        mutableStateOf("")
    }

    LaunchedEffect(pet.donoId) {
        petRepository.buscarDadosDono(pet.donoId) { nome, telefone ->
            nomeDono = nome
            telefoneDono = telefone
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Fotos do animal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
            ) {

                if (fotos.isEmpty()) {

                    PetImage(
                        fotoBase64 = "",
                        emoji = pet.emoji,
                        emojiSize = 110.sp,
                        modifier = Modifier.fillMaxSize()
                    )

                } else {

                    val pagerState = rememberPagerState(
                        pageCount = { fotos.size }
                    )

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->

                        PetImage(
                            fotoBase64 = fotos[page],
                            emoji = pet.emoji,
                            emojiSize = 110.sp,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    if (fotos.size > 1) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(
                                    start = 20.dp,
                                    bottom = 38.dp
                                )
                                .background(
                                    TextoPrincipal.copy(alpha = 0.6f),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 4.dp
                                )
                        ) {
                            Text(
                                text = "${pagerState.currentPage + 1}/${fotos.size}",
                                color = Branco,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Botão voltar
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                        .size(42.dp)
                        .shadow(4.dp, CircleShape)
                        .background(
                            Branco.copy(alpha = 0.9f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = TextoPrincipal
                    )
                }
            }

            // Conteúdo do animal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-24).dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 28.dp,
                                topEnd = 28.dp
                            )
                        )
                        .background(Fundo)
                        .padding(
                            horizontal = 24.dp,
                            vertical = 24.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    // Nome e localização
                    Column(
                        modifier = Modifier.padding(end = 56.dp)
                    ) {
                        Text(
                            text = pet.nome,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (pet.localidade.isNotBlank()) {
                                "📍 ${pet.localidade}"
                            } else {
                                "📍 Localidade não informada"
                            },
                            fontSize = 14.sp,
                            color = TextoSecundario
                        )
                    }

                    // Características
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        InfoBoxCard(
                            titulo = "Espécie",
                            valor = pet.especie,
                            modifier = Modifier.weight(1f)
                        )

                        InfoBoxCard(
                            titulo = "Porte",
                            valor = pet.porte,
                            modifier = Modifier.weight(1f)
                        )

                        InfoBoxCard(
                            titulo = "Idade",
                            valor = pet.idade,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Informações do protetor
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Branco
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(VerdeClaro),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = nomeDono
                                        .firstOrNull()
                                        ?.uppercase() ?: "?",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VerdeEscuro
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = nomeDono,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = "Protetor(a) do Pet",
                                    fontSize = 12.sp,
                                    color = TextoSecundario
                                )
                            }

                            if (telefoneDono.isNotBlank()) {
                                Button(
                                    onClick = {
                                        val telefone = Uri.encode(telefoneDono)
                                        val intent = Intent(
                                            Intent.ACTION_DIAL,
                                            Uri.parse("tel:$telefone")
                                        )
                                        context.startActivity(intent)
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = VerdePrincipal
                                    ),
                                    contentPadding = PaddingValues(
                                        horizontal = 14.dp,
                                        vertical = 8.dp
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = "Ligar",
                                        tint = Branco,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(
                                        text = "Ligar",
                                        color = Branco,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }

                    // Descrição
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Sobre o pet",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )

                        Text(
                            text = pet.descricao.ifBlank {
                                "Um companheiro carinhoso esperando por uma família para chamar de sua!"
                            },
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = TextoSecundario
                        )
                    }

                    Spacer(modifier = Modifier.height(72.dp))
                }

                // Botão de favorito
                IconButton(
                    onClick = onToggleFavorito,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 24.dp)
                        .offset(y = (-22).dp)
                        .size(48.dp)
                        .shadow(6.dp, CircleShape)
                        .background(Branco, CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorito) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = if (isFavorito) {
                            "Remover dos favoritos"
                        } else {
                            "Adicionar aos favoritos"
                        },
                        tint = if (isFavorito) {
                            Vermelho
                        } else {
                            TextoSecundario
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Barra inferior de adoção
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Fundo,
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = onAdopt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 14.dp
                    )
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdePrincipal
                )
            ) {
                Text(
                    text = "Quero Adotar ❤️",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Branco
                )
            }
        }
    }
}

@Composable
private fun InfoBoxCard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Branco
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titulo,
                fontSize = 12.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = valor.ifBlank { "-" },
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
