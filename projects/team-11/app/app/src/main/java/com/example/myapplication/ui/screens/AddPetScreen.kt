package com.example.myapplication.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Pet
import com.example.myapplication.ui.components.PetImage
import com.example.myapplication.ui.components.PetTextField
import com.example.myapplication.ui.theme.*
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    onSave: (Pet) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("Cachorro") }
    var idade by remember { mutableStateOf("") }
    var porte by remember { mutableStateOf("Médio") }
    var localidade by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var listaFotos by remember { mutableStateOf<List<String>>(emptyList()) }

    val emojiSelecionado = if (especie == "Gato") "🐱" else "🐶"

    val galeriaMultiplaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            val novasFotos = uris.take(4 - listaFotos.size).mapNotNull { uri ->
                try {
                    val bitmapOriginal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    }
                    val reduzido = Bitmap.createScaledBitmap(bitmapOriginal, 320, 320, true)
                    val stream = ByteArrayOutputStream()
                    reduzido.compress(Bitmap.CompressFormat.JPEG, 65, stream)
                    Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
                } catch (e: Exception) {
                    null
                }
            }
            listaFotos = (listaFotos + novasFotos).take(4)
        }
    }

    Scaffold(
        containerColor = Fundo,
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Pet", fontWeight = FontWeight.Bold, color = TextoPrincipal) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = TextoPrincipal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Fundo)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Fotos do Pet (${listaFotos.size}/4)",
                fontWeight = FontWeight.SemiBold,
                color = TextoPrincipal
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (listaFotos.size < 4) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(VerdeClaro)
                            .clickable { galeriaMultiplaLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Add, contentDescription = "Adicionar fotos", tint = VerdeEscuro)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Adicionar", fontSize = 12.sp, color = VerdeEscuro, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                listaFotos.forEachIndexed { index, fotoBase64 ->
                    Box(modifier = Modifier.size(100.dp)) {
                        PetImage(
                            fotoBase64 = fotoBase64,
                            emoji = emojiSelecionado,
                            emojiSize = 40.sp,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        )
                        IconButton(
                            onClick = {
                                listaFotos = listaFotos.filterIndexed { i, _ -> i != index }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(24.dp)
                                .background(TextoPrincipal.copy(alpha = 0.7f), CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Remover", tint = Branco, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(selected = especie == "Cachorro", onClick = { especie = "Cachorro" }, label = { Text("🐶 Cachorro") })
                FilterChip(selected = especie == "Gato", onClick = { especie = "Gato" }, label = { Text("🐱 Gato") })
            }

            PetTextField(value = nome, onValueChange = { nome = it }, label = "Nome do Pet")
            PetTextField(value = idade, onValueChange = { idade = it }, label = "Idade (ex: 2 anos)")
            PetTextField(value = localidade, onValueChange = { localidade = it }, label = "Cidade / Bairro")

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Pequeno", "Médio", "Grande").forEach { item ->
                    FilterChip(selected = porte == item, onClick = { porte = item }, label = { Text(item) })
                }
            }

            PetTextField(value = descricao, onValueChange = { descricao = it }, label = "Descrição / Temperamento")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    onSave(
                        Pet(
                            nome = nome,
                            especie = especie,
                            idade = idade,
                            porte = porte,
                            localidade = localidade,
                            descricao = descricao,
                            emoji = emojiSelecionado,
                            fotoBase64 = listaFotos.firstOrNull() ?: "",
                            fotos = listaFotos
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal)
            ) {
                Text("Publicar para Adoção", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Branco)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
