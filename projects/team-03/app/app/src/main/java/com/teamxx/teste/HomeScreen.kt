package com.teamxx.teste

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamxx.teste.ui.theme.TesteTheme

/**
 * Tela principal [WifiMapperHomeScreen]: exibe a identidade visual do app,
 * o radar com desenho em Canvas, o chip de status qualitativo, o botão
 * para sortear/verificar o sinal e o botão de navegação para a tela de detalhes.
 *
 * @param status Estado atual da medição de sinal.
 * @param onStatusChange Callback disparado ao acionar uma nova medição.
 * @param onNavigateToDetails Callback disparado ao tocar em "Ver Detalhes".
 * @param modifier Modificador de layout repassado pelo container.
 */
@Composable
fun WifiMapperHomeScreen(
    status: SignalStatus,
    onStatusChange: (SignalStatus) -> Unit,
    onNavigateToDetails: (SignalStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B111E))
    ) {
        // ================================================================================
        // CABEÇALHO (Logotipo com ícone de Wi-Fi, Nome do App e Subtítulo)
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
        // CONTEÚDO CENTRAL (Título, Slogan, Radar, Chip de Status e Botões de Ação)
        // ================================================================================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Verificação de Sinal",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Mapeie a rede. Conecte no melhor ponto.",
                fontSize = 15.sp,
                color = Color(0xFF00D2E5),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Radar em Canvas que reflete o status atual
            RadarView(
                status = status,
                modifier = Modifier.size(260.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Chip com a classificação qualitativa
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF131C2D))
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = status.label,
                    color = status.textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ============================================================================
            // BOTÃO DE AÇÃO PRIMÁRIA: Iniciar Verificação (Dispara alteração do Estado)
            // ============================================================================
            Button(
                onClick = {
                    val availableStatuses = listOf(
                        SignalStatus.STRONG,
                        SignalStatus.MEDIUM,
                        SignalStatus.WEAK
                    )
                    onStatusChange(availableStatuses.random())
                },
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
                        text = "▶",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Iniciar Verificação",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            // ============================================================================
            // BOTÃO DE NAVEGAÇÃO: Ver Detalhes (Habilitado quando status != NONE)
            // ============================================================================
            if (status != SignalStatus.NONE) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onNavigateToDetails(status) },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF131C2D),
                        contentColor = Color(0xFF00E5FF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(1.dp, Color(0xFF1E304B), RoundedCornerShape(14.dp))
                ) {
                    Text(
                        text = "Ver Detalhes",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF00E5FF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

/**
 * Radar vetorial desenhado via primitivas geométricas no [Canvas].
 */
@Composable
fun RadarView(
    status: SignalStatus,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerOffset = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = size.width / 2f
            val gridColor = Color(0xFF1E2D44)
            val strokeWidth = 1.5.dp.toPx()

            // Setor iluminado quando uma medição estiver ativa
            if (status != SignalStatus.NONE) {
                drawArc(
                    color = status.sectorColor.copy(alpha = 0.35f),
                    startAngle = -90f,
                    sweepAngle = 60f,
                    useCenter = true,
                    topLeft = Offset(0f, 0f),
                    size = size
                )
            }

            // Círculos concêntricos da grade do radar
            drawCircle(
                color = gridColor,
                radius = maxRadius,
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )
            drawCircle(
                color = gridColor,
                radius = maxRadius * 0.68f,
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )
            drawCircle(
                color = gridColor,
                radius = maxRadius * 0.36f,
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )

            // Retas de mira ortogonais
            drawLine(
                color = gridColor,
                start = Offset(0f, centerOffset.y),
                end = Offset(size.width, centerOffset.y),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = gridColor,
                start = Offset(centerOffset.x, 0f),
                end = Offset(centerOffset.x, size.height),
                strokeWidth = strokeWidth
            )
        }

        // Elementos centrais: Ícone, Valor dBm e Unidade
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WifiIcon(
                color = status.textColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (status.dbm != null) "${status.dbm}" else "—",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "DBM",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF7E92A8),
                letterSpacing = 1.sp
            )
        }
    }
}

/**
 * Ícone estilizado de Wi-Fi desenhado com arcos em [Canvas].
 */
@Composable
fun WifiIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.12f
        val w = size.width
        val h = size.height

        // Onda superior
        drawArc(
            color = color,
            startAngle = 205f,
            sweepAngle = 130f,
            useCenter = false,
            topLeft = Offset(0f, h * 0.12f),
            size = Size(w, h * 0.95f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Onda média
        drawArc(
            color = color,
            startAngle = 205f,
            sweepAngle = 130f,
            useCenter = false,
            topLeft = Offset(w * 0.22f, h * 0.38f),
            size = Size(w * 0.56f, h * 0.65f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Antena base
        drawCircle(
            color = color,
            radius = strokeWidth * 0.85f,
            center = Offset(w * 0.5f, h * 0.84f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WifiMapperHomeScreenPreview() {
    TesteTheme {
        WifiMapperHomeScreen(
            status = SignalStatus.MEDIUM,
            onStatusChange = {},
            onNavigateToDetails = {}
        )
    }
}
