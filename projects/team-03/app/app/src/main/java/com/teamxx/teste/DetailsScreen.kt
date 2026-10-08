package com.teamxx.teste

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamxx.teste.ui.theme.TesteTheme

/**
 * Tela secundária [DetailsScreen]: exibe o detalhamento completo do resultado da verificação
 * de sinal Wi-Fi (status qualitativo, intensidade numérica em dBm e recomendação prática),
 * acompanhada do botão de retorno para a tela inicial.
 *
 * @param status Nível de sinal verificado a ser detalhado.
 * @param onBack Callback disparado ao acionar o botão de retorno.
 * @param modifier Modificador de layout.
 */
@Composable
fun DetailsScreen(
    status: SignalStatus,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B111E))
    ) {
        // ================================================================================
        // CABEÇALHO (Logotipo com ícone de Wi-Fi e Título do Aplicativo)
        // ================================================================================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF142033))
                    .border(1.dp, Color(0xFF1E304B), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                WifiIcon(
                    color = Color(0xFF00E5FF),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Wi-Fi Mapper",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "SIGNAL DIAGNOSTICS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF7E92A8),
                    letterSpacing = 1.2.sp
                )
            }
        }

        HorizontalDivider(
            color = Color(0xFF141F33),
            thickness = 1.dp
        )

        // ================================================================================
        // CONTEÚDO PRINCIPAL (Título e Card com Diagnóstico Completo)
        // ================================================================================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Detalhes da Verificação",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Card informativo estilizado com os dados do sinal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF131C2D))
                    .border(1.dp, Color(0xFF1E304B), RoundedCornerShape(20.dp))
                    .padding(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // Linha 1: Status Qualitativo
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Status",
                            fontSize = 15.sp,
                            color = Color(0xFF8FA3B8),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = status.label,
                            fontSize = 16.sp,
                            color = status.textColor,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HorizontalDivider(
                        color = Color(0xFF1E2D44),
                        thickness = 1.dp
                    )

                    // Linha 2: Intensidade Numérica em dBm
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Intensidade",
                            fontSize = 15.sp,
                            color = Color(0xFF8FA3B8),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (status.dbm != null) "${status.dbm} dBm" else "—",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HorizontalDivider(
                        color = Color(0xFF1E2D44),
                        thickness = 1.dp
                    )

                    // Linha 3: Recomendação Textual Prática
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Recomendação",
                            fontSize = 13.sp,
                            color = Color(0xFF8FA3B8),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = status.recommendation,
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ============================================================================
            // BOTÃO DE RETORNO (popBackStack para voltar à tela anterior)
            // ============================================================================
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E5FF),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "←",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    TesteTheme {
        DetailsScreen(
            status = SignalStatus.MEDIUM,
            onBack = {}
        )
    }
}
