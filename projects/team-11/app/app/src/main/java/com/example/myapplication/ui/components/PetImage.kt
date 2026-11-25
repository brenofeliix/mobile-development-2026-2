package com.example.myapplication.ui.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import com.example.myapplication.ui.theme.VerdeClaro

@Composable
fun PetImage(
    fotoBase64: String,
    emoji: String,
    emojiSize: TextUnit,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(fotoBase64) {
        if (fotoBase64.isNotBlank()) {
            try {
                val bytes = Base64.decode(fotoBase64, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
            } catch (e: Exception) {
                null
            }
        } else null
    }

    Box(
        modifier = modifier.background(VerdeClaro),
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = "Foto do pet",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(text = emoji, fontSize = emojiSize)
        }
    }
}
