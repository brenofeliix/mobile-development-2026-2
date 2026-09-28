// Define o pacote ao qual esta classe pertence dentro da estrutura do projeto
package com.teamxx.teste

// Importa a classe Bundle usada pelo ciclo de vida do Android para salvar o estado da Activity
import android.os.Bundle
// Importa ComponentActivity, a classe base moderna para atividades que usam Jetpack Compose
import androidx.activity.ComponentActivity
// Importa setContent, a função que liga o Jetpack Compose à janela da Activity
import androidx.activity.compose.setContent
// Importa enableEdgeToEdge para desenhar sob as barras do sistema (status bar e navigation bar)
import androidx.activity.enableEdgeToEdge
// Importa Canvas para desenhar gráficos vetoriais personalizados na tela (círculos, arcos e retas)
import androidx.compose.foundation.Canvas
// Importa o modificador background para definir a cor de fundo de qualquer componente
import androidx.compose.foundation.background
// Importa o modificador border para aplicar contorno/borda estilizada em componentes
import androidx.compose.foundation.border
// Importa Arrangement para definir como os itens se distribuem no espaço (SpaceEvenly, Center, etc.)
import androidx.compose.foundation.layout.Arrangement
// Importa Box para empilhar componentes uns sobre os outros ou alinhar conteúdo livremente
import androidx.compose.foundation.layout.Box
// Importa Column para posicionar elementos um embaixo do outro (layout vertical)
import androidx.compose.foundation.layout.Column
// Importa Row para posicionar elementos um ao lado do outro (layout horizontal)
import androidx.compose.foundation.layout.Row
// Importa Spacer para criar espaços em branco vazios com largura ou altura definidas
import androidx.compose.foundation.layout.Spacer
// Importa fillMaxSize para fazer o componente ocupar 100% da largura e altura disponíveis
import androidx.compose.foundation.layout.fillMaxSize
// Importa fillMaxWidth para fazer o componente ocupar 100% da largura horizontal disponível
import androidx.compose.foundation.layout.fillMaxWidth
// Importa height para definir uma altura fixa em dp
import androidx.compose.foundation.layout.height
// Importa padding para aplicar margens internas (espaçamento) ao redor do componente
import androidx.compose.foundation.layout.padding
// Importa size para definir largura e altura simultaneamente com o mesmo valor
import androidx.compose.foundation.layout.size
// Importa width para definir uma largura fixa em dp
import androidx.compose.foundation.layout.width
// Importa CircleShape para cortar componentes no formato de um círculo perfeito
import androidx.compose.foundation.shape.CircleShape
// Importa RoundedCornerShape para arredondar os cantos de cartões, botões e caixas
import androidx.compose.foundation.shape.RoundedCornerShape
// Importa Button, o componente oficial do Material 3 para botões interativos
import androidx.compose.material3.Button
// Importa ButtonDefaults para customizar as propriedades padrão do botão (como cores)
import androidx.compose.material3.ButtonDefaults
// Importa HorizontalDivider para desenhar uma linha divisória horizontal fina entre seções
import androidx.compose.material3.HorizontalDivider
// Importa Scaffold que fornece o esqueleto básico de tela conforme as diretrizes do Material
import androidx.compose.material3.Scaffold
// Importa Text para renderizar textos tipográficos na tela
import androidx.compose.material3.Text
// Importa @Composable, a anotação que transforma qualquer função em um componente de interface
import androidx.compose.runtime.Composable
// Importa getValue para permitir a sintaxe de delegação 'by' ao ler variáveis de estado
import androidx.compose.runtime.getValue
// Importa mutableStateOf para criar uma variável cujo valor, ao mudar, redesenha a tela
import androidx.compose.runtime.mutableStateOf
// Importa remember para fazer o Compose reter o valor da variável durante as recomposições
import androidx.compose.runtime.remember
// Importa setValue para permitir a sintaxe de delegação 'by' ao atribuir novos valores ao estado
import androidx.compose.runtime.setValue
// Importa Alignment para alinhar itens (Start, Center, End, CenterHorizontally, etc.)
import androidx.compose.ui.Alignment
// Importa Modifier, o objeto essencial do Compose para alterar tamanho, estilo e comportamento
import androidx.compose.ui.Modifier
// Importa clip para recortar a forma geométrica de um componente (círculo, cantos redondos)
import androidx.compose.ui.draw.clip
// Importa Offset para representar coordenadas cartesianas (X, Y) no plano de desenho
import androidx.compose.ui.geometry.Offset
// Importa Size para representar dimensões de largura e altura no desenho em Canvas
import androidx.compose.ui.geometry.Size
// Importa Color para definir cores utilizando hexadecimal ou paletas do sistema
import androidx.compose.ui.graphics.Color
// Importa StrokeCap para definir o formato de acabamento das linhas (arredondado, reto)
import androidx.compose.ui.graphics.StrokeCap
// Importa Stroke para desenhar apenas o contorno (sem preencher o interior da forma)
import androidx.compose.ui.graphics.drawscope.Stroke
// Importa FontWeight para escolher o peso da fonte (Normal, Medium, SemiBold, Bold)
import androidx.compose.ui.text.font.FontWeight
// Importa TextAlign para justificar ou centralizar textos dentro de sua caixa
import androidx.compose.ui.text.style.TextAlign
// Importa Preview para visualizar o Composable diretamente no Android Studio
import androidx.compose.ui.tooling.preview.Preview
// Importa a unidade 'dp' (Density-independent Pixels) usada para tamanhos e margens na tela
import androidx.compose.ui.unit.dp
// Importa a unidade 'sp' (Scale-independent Pixels) usada exclusivamente para tamanhos de texto
import androidx.compose.ui.unit.sp
// Importa o tema visual TesteTheme configurado na pasta ui/theme
import com.teamxx.teste.ui.theme.TesteTheme

// ========================================================================================
// 1. MODELO DE DADOS E ESTADO (Sprint 02)
// ========================================================================================

/**
 * Enumeração [SignalStatus]: representa cada um dos 4 possíveis estados da medição de sinal.
 *
 * @param label Texto exibido no chip de status (ex: "Aguardando", "Sinal Forte").
 * @param dbm Valor numérico simulado em dBm (ou null quando não houver medição).
 * @param sectorColor Cor do setor de varredura desenhado no Canvas do Radar.
 * @param textColor Cor do texto informativo, do chip e do ícone de Wi-Fi central.
 */
enum class SignalStatus(
    val label: String,       // Nome amigável do status para o usuário
    val dbm: Int?,           // Medição simulada da intensidade em decibéis-miliwatts
    val sectorColor: Color,  // Cor de iluminação do radar
    val textColor: Color     // Cor temática da tipografia e ícone
) {
    // Estado inicial: nenhuma medição foi feita ainda ("Aguardando" e traço "— DBM")
    NONE(
        label = "Aguardando",
        dbm = null,
        sectorColor = Color.Transparent, // Sem iluminação no radar
        textColor = Color(0xFF8FA3B8)    // Cinza azulado suave
    ),
    // Estado de sinal forte: conexão estável e veloz (-45 dBm)
    STRONG(
        label = "Sinal Forte",
        dbm = -45,
        sectorColor = Color(0xFF4CAF50), // Verde indicador de excelente conexão
        textColor = Color(0xFF81C784)
    ),
    // Estado de sinal médio: conexão intermediária (-68 dBm)
    MEDIUM(
        label = "Sinal Médio",
        dbm = -68,
        sectorColor = Color(0xFFFF9800), // Laranja indicando atenção
        textColor = Color(0xFFFFB74D)
    ),
    // Estado de sinal fraco: conexão instável com perdas (-85 dBm)
    WEAK(
        label = "Sinal Fraco",
        dbm = -85,
        sectorColor = Color(0xFFE53935), // Vermelho alertando conexão ruim
        textColor = Color(0xFFFF5252)
    )
}

// ========================================================================================
// 2. ACTIVITY PRINCIPAL DO ANDROID
// ========================================================================================

/**
 * Classe [MainActivity]: primeira tela inicializada pelo Android ao abrir o aplicativo.
 */
class MainActivity : ComponentActivity() {

    // Método onCreate: primeiro método executado no ciclo de vida da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        // Executa a inicialização padrão da superclasse ComponentActivity
        super.onCreate(savedInstanceState)

        // Ativa o modo borda-a-borda (desenho atrás da barra de status e de navegação)
        enableEdgeToEdge()

        // setContent: acopla a hierarquia de componentes do Jetpack Compose à janela
        setContent {
            // Aplica as definições globais de cores e fontes do aplicativo
            TesteTheme {
                // Scaffold: estrutura visual base do Material Design 3
                Scaffold(
                    // Configura o modifier para preencher 100% da tela com fundo azul escuro
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0B111E))
                ) { innerPadding ->
                    // innerPadding: margens de segurança fornecidas pelo Scaffold
                    WifiMapperHomeScreen(
                        // Aplica o padding seguro à nossa tela principal
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// ========================================================================================
// 3. TELA PRINCIPAL (Composable com Estado Reativo)
// ========================================================================================

/**
 * Função Composable [WifiMapperHomeScreen]: desenha toda a interface visual e gerencia o estado.
 */
@Composable
fun WifiMapperHomeScreen(modifier: Modifier = Modifier) {

    // VARIÁVEL DE ESTADO REATIVO (Core da Sprint 02):
    // 'remember' preserva o estado durante recomposições de tela.
    // 'mutableStateOf' notifica o Compose para redesenhar a tela quando o valor for alterado.
    var signalStatus by remember { mutableStateOf(SignalStatus.NONE) }

    // Column principal: empilha o cabeçalho e o conteúdo central verticalmente
    Column(
        modifier = modifier
            .fillMaxSize()                  // Ocupa toda a altura e largura
            .background(Color(0xFF0B111E))  // Cor de fundo do app (azul noite escuro)
    ) {

        // --------------------------------------------------------------------------------
        // SEÇÃO DO CABEÇALHO (Header Superior com Logotipo e Título)
        // --------------------------------------------------------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()                                 // Ocupa toda a largura horizontal
                .padding(horizontal = 20.dp, vertical = 16.dp), // Espaçamento interno das margens
            verticalAlignment = Alignment.CenterVertically      // Alinha ícone e texto na mesma linha central
        ) {
            // Box circular para o fundo do ícone de Wi-Fi
            Box(
                modifier = Modifier
                    .size(44.dp)                                      // Tamanho de 44x44 dp
                    .clip(CircleShape)                                // Corta em formato de círculo
                    .background(Color(0xFF142033))                    // Cor de fundo do círculo
                    .border(1.dp, Color(0xFF1E304B), CircleShape),    // Borda circular sutil de 1 dp
                contentAlignment = Alignment.Center                   // Centraliza o ícone dentro do círculo
            ) {
                // Chama a Composable personalizada que desenha o ícone vetorial de Wi-Fi em ciano
                WifiIcon(
                    color = Color(0xFF00E5FF),
                    modifier = Modifier.size(22.dp)
                )
            }

            // Espaço vazio de 14 dp entre o ícone circular e os textos ao lado
            Spacer(modifier = Modifier.width(14.dp))

            // Column para colocar o nome do app e o subtítulo um sobre o outro
            Column {
                // Nome do Aplicativo (RF-01 da Sprint 01)
                Text(
                    text = "Wi-Fi Mapper",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                // Subtítulo descritivo em letras maiúsculas
                Text(
                    text = "SIGNAL DIAGNOSTICS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF7E92A8),
                    letterSpacing = 1.2.sp // Espaçamento estilizado entre os caracteres
                )
            }
        }

        // Linha divisória horizontal sutil separando o cabeçalho do restante da tela
        HorizontalDivider(
            color = Color(0xFF141F33),
            thickness = 1.dp
        )

        // --------------------------------------------------------------------------------
        // CONTEÚDO PRINCIPAL (Título, Slogan, Radar, Status e Botão)
        // --------------------------------------------------------------------------------
        Column(
            modifier = Modifier
                .fillMaxSize()                                  // Preenche o restante da tela
                .padding(horizontal = 24.dp, vertical = 20.dp), // Margens laterais e verticais
            horizontalAlignment = Alignment.CenterHorizontally  // Centraliza todos os filhos horizontalmente
        ) {
            // Espaçamento vertical de 24 dp no topo
            Spacer(modifier = Modifier.height(24.dp))

            // Título principal da funcionalidade
            Text(
                text = "Verificação de Sinal",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            // Espaçamento de 8 dp entre título e slogan
            Spacer(modifier = Modifier.height(8.dp))

            // Slogan / Proposta de valor do produto (RF-02 da Sprint 01)
            Text(
                text = "Mapeie a rede. Conecte no melhor ponto.",
                fontSize = 15.sp,
                color = Color(0xFF00D2E5), // Ciano de destaque
                textAlign = TextAlign.Center
            )

            // Espaçamento de 36 dp antes do radar
            Spacer(modifier = Modifier.height(36.dp))

            // ----------------------------------------------------------------------------
            // COMPONENTE DO RADAR (Reage ao estado 'signalStatus')
            // ----------------------------------------------------------------------------
            RadarView(
                status = signalStatus,          // Passa o estado atual para o radar
                modifier = Modifier.size(270.dp) // Define o diâmetro do radar como 270x270 dp
            )

            // Espaçamento de 28 dp entre o radar e o chip de status
            Spacer(modifier = Modifier.height(28.dp))

            // ----------------------------------------------------------------------------
            // CHIP DE STATUS (Pílula arredondada com o texto do estado atual)
            // ----------------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))                      // Formato de pílula arredondada
                    .background(Color(0xFF131C2D))                     // Fundo escuro azulado
                    .padding(horizontal = 24.dp, vertical = 10.dp),    // Padding interno
                contentAlignment = Alignment.Center
            ) {
                // Texto do status reativo: "Aguardando", "Sinal Forte", "Sinal Médio" ou "Sinal Fraco"
                Text(
                    text = signalStatus.label,
                    color = signalStatus.textColor, // Cor dinâmica conforme o status
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Espaçador flexível com peso 1: empurra tudo que está abaixo para o fim da tela
            Spacer(modifier = Modifier.weight(1f))

            // ----------------------------------------------------------------------------
            // BOTÃO DE AÇÃO PRIMÁRIA (Dispara o evento que atualiza o estado)
            // ----------------------------------------------------------------------------
            Button(
                // onClick: código executado quando o usuário toca no botão
                onClick = {
                    // Lista com os status possíveis para simular uma nova medição
                    val availableStatuses = listOf(
                        SignalStatus.STRONG,
                        SignalStatus.MEDIUM,
                        SignalStatus.WEAK
                    )
                    // Sorteia aleatoriamente um dos 3 status e atualiza a variável de estado.
                    // A alteração de 'signalStatus' faz o Compose recompor a tela imediatamente!
                    signalStatus = availableStatuses.random()
                },
                // Cantos arredondados de 14 dp no botão
                shape = RoundedCornerShape(14.dp),
                // Configuração das cores do botão
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E5FF), // Fundo ciano luminoso
                    contentColor = Color.Black          // Texto e ícone em preto para alto contraste
                ),
                modifier = Modifier
                    .fillMaxWidth()   // Botão estica em toda a largura útil
                    .height(56.dp)    // Altura ergonômica de 56 dp para toque fácil
            ) {
                // Row interna do botão para colocar o ícone de Play e o texto lado a lado
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Ícone de triângulo Play
                    Text(
                        text = "▶",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    // Espaçamento de 8 dp entre o triângulo e a palavra
                    Spacer(modifier = Modifier.width(8.dp))
                    // Texto do botão de ação primária (RF-04)
                    Text(
                        text = "Iniciar Verificação",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            // Espaçamento de 16 dp na base inferior da tela
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ========================================================================================
// 4. RADAR EM CANVAS (Desenho Vetorial Geométrico)
// ========================================================================================

/**
 * Função Composable [RadarView]: renderiza a grade e animações do radar gráfico.
 *
 * @param status O estado atual do sinal, usado para colorir o setor e definir os textos centrais.
 * @param modifier Modificador de tamanho e layout do radar.
 */
@Composable
fun RadarView(
    status: SignalStatus,
    modifier: Modifier = Modifier
) {
    // Box permite sobrepor o desenho em Canvas e os textos centrais no mesmo espaço
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center // Centraliza todo o conteúdo no meio do Box
    ) {
        // Canvas: área de desenho vetorial nativo
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Calcula o ponto central (X, Y) do radar
            val centerOffset = Offset(size.width / 2f, size.height / 2f)
            // Calcula o raio máximo (metade da largura do Canvas)
            val maxRadius = size.width / 2f
            // Cor das linhas da grade
            val gridColor = Color(0xFF1E2D44)
            // Espessura do traço convertida de dp para pixels (1.5 dp)
            val strokeWidth = 1.5.dp.toPx()

            // ----------------------------------------------------------------------------
            // 1. SETOR CIRCULAR COLORIDO (Arco em fatia de pizza iluminada)
            // Só desenha se houver uma medição ativa (diferente de NONE)
            // ----------------------------------------------------------------------------
            if (status != SignalStatus.NONE) {
                drawArc(
                    color = status.sectorColor.copy(alpha = 0.35f), // Opacidade suave de 35%
                    startAngle = -90f,                             // Começa no topo (posição de 12 horas)
                    sweepAngle = 60f,                              // Varre um ângulo de 60 graus para a direita
                    useCenter = true,                              // Conecta as pontas ao centro para formar uma fatia
                    topLeft = Offset(0f, 0f),                      // Ponto de início do retângulo delimitador
                    size = size                                    // Ocupa todo o tamanho do Canvas
                )
            }

            // ----------------------------------------------------------------------------
            // 2. TRÊS CÍRCULOS CONCÊNTRICOS DA GRADE DO RADAR
            // ----------------------------------------------------------------------------
            // Círculo mais externo (100% do raio)
            drawCircle(
                color = gridColor,
                radius = maxRadius,
                center = centerOffset,
                style = Stroke(width = strokeWidth) // Desenha apenas o contorno
            )
            // Círculo intermediário (68% do raio)
            drawCircle(
                color = gridColor,
                radius = maxRadius * 0.68f,
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )
            // Círculo interno (36% do raio)
            drawCircle(
                color = gridColor,
                radius = maxRadius * 0.36f,
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )

            // ----------------------------------------------------------------------------
            // 3. RETAS DE MIRA ORTOGONAIS (Cruz central)
            // ----------------------------------------------------------------------------
            // Linha horizontal de mira (da ponta esquerda até a ponta direita)
            drawLine(
                color = gridColor,
                start = Offset(0f, centerOffset.y),
                end = Offset(size.width, centerOffset.y),
                strokeWidth = strokeWidth
            )
            // Linha vertical de mira (da ponta superior até a ponta inferior)
            drawLine(
                color = gridColor,
                start = Offset(centerOffset.x, 0f),
                end = Offset(centerOffset.x, size.height),
                strokeWidth = strokeWidth
            )
        }

        // --------------------------------------------------------------------------------
        // 4. ELEMENTOS CENTRALIZADOS NO CENTRO DO RADAR
        // --------------------------------------------------------------------------------
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Desenho do ícone de Wi-Fi central na cor do status atual
            WifiIcon(
                color = status.textColor,
                modifier = Modifier.size(24.dp)
            )

            // Espaçamento vertical de 4 dp
            Spacer(modifier = Modifier.height(4.dp))

            // Exibição do valor em dBm (ex: "-85" ou traço "—" quando em espera)
            Text(
                text = if (status.dbm != null) "${status.dbm}" else "—",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Rótulo da unidade de medida do sinal
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

// ========================================================================================
// 5. DESENHO VETORIAL DO ÍCONE DE WI-FI EM CANVAS
// ========================================================================================

/**
 * Função Composable [WifiIcon]: constrói vetorialmente as ondas e o ponto do ícone de Wi-Fi.
 *
 * @param color Cor aplicada aos traços e ao ponto central.
 * @param modifier Modificador de tamanho e espaçamento do ícone.
 */
@Composable
fun WifiIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        // Define a largura do traço como 12% da largura do ícone
        val strokeWidth = size.width * 0.12f
        val w = size.width   // Largura total da área do ícone
        val h = size.height  // Altura total da área do ícone

        // Onda superior (arco mais externo)
        drawArc(
            color = color,
            startAngle = 205f,                                  // Ângulo inicial do arco
            sweepAngle = 130f,                                  // Abertura angular do arco
            useCenter = false,                                  // Não fecha o arco até o centro
            topLeft = Offset(0f, h * 0.12f),                    // Posição no topo
            size = Size(w, h * 0.95f),                          // Dimensão do arco
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round) // Pontas arredondadas
        )

        // Onda intermediária (arco central)
        drawArc(
            color = color,
            startAngle = 205f,
            sweepAngle = 130f,
            useCenter = false,
            topLeft = Offset(w * 0.22f, h * 0.38f),
            size = Size(w * 0.56f, h * 0.65f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Ponto central inferior da antena de transmissão
        drawCircle(
            color = color,
            radius = strokeWidth * 0.85f,                       // Raio proporcional do círculo
            center = Offset(w * 0.5f, h * 0.84f)                // Ponto de posicionamento central
        )
    }
}

// ========================================================================================
// 6. PRÉ-VISUALIZAÇÃO NO ANDROID STUDIO
// ========================================================================================

/**
 * Função [WifiMapperHomeScreenPreview]: renderiza o preview do Compose na IDE Android Studio.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WifiMapperHomeScreenPreview() {
    // Envolve a tela com o tema para exibir cores e fontes corretas no editor visual
    TesteTheme {
        WifiMapperHomeScreen()
    }
}