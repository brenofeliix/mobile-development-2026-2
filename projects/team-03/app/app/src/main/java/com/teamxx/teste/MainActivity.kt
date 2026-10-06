// Define o pacote ao qual esta classe pertence dentro da estrutura de diretórios do projeto
package com.teamxx.teste

// Importa a classe Bundle usada pelo ciclo de vida do Android para salvar e restaurar o estado da Activity
import android.os.Bundle
// Importa ComponentActivity, a classe base moderna para atividades que usam Jetpack Compose
import androidx.activity.ComponentActivity
// Importa setContent, a função de extensão que conecta a hierarquia Composable à janela da Activity
import androidx.activity.compose.setContent
// Importa enableEdgeToEdge para permitir que o app desenhe sob as barras de status e navegação do sistema
import androidx.activity.enableEdgeToEdge
// Importa Canvas para desenhar formas geométricas vetoriais personalizadas diretamente na tela
import androidx.compose.foundation.Canvas
// Importa o modificador background para definir a cor de fundo de qualquer componente visual
import androidx.compose.foundation.background
// Importa o modificador border para desenhar bordas e contornos customizados ao redor de componentes
import androidx.compose.foundation.border
// Importa Arrangement para controlar o alinhamento e distribuição de elementos em Rows e Columns
import androidx.compose.foundation.layout.Arrangement
// Importa Box para empilhar componentes uns sobre os outros no eixo Z ou alinhar conteúdo livremente
import androidx.compose.foundation.layout.Box
// Importa Column para organizar componentes em uma coluna vertical (um embaixo do outro)
import androidx.compose.foundation.layout.Column
// Importa Row para organizar componentes em uma linha horizontal (um ao lado do outro)
import androidx.compose.foundation.layout.Row
// Importa Spacer para criar espaços vazios configuráveis com largura ou altura específicas
import androidx.compose.foundation.layout.Spacer
// Importa fillMaxSize para fazer o componente ocupar 100% da largura e altura disponíveis
import androidx.compose.foundation.layout.fillMaxSize
// Importa fillMaxWidth para fazer o componente ocupar 100% da largura horizontal disponível
import androidx.compose.foundation.layout.fillMaxWidth
// Importa height para definir uma altura fixa em dp para o componente
import androidx.compose.foundation.layout.height
// Importa padding para aplicar espaçamento interno (margens de respiro) ao redor do componente
import androidx.compose.foundation.layout.padding
// Importa size para definir largura e altura simultaneamente com o mesmo valor em dp
import androidx.compose.foundation.layout.size
// Importa width para definir uma largura fixa em dp para o componente
import androidx.compose.foundation.layout.width
// Importa CircleShape para recortar componentes no formato de um círculo geométrico perfeito
import androidx.compose.foundation.shape.CircleShape
// Importa RoundedCornerShape para aplicar cantos arredondados em caixas, botões e cartões
import androidx.compose.foundation.shape.RoundedCornerShape
// Importa Button, o componente interativo oficial do Material Design 3 para cliques e ações
import androidx.compose.material3.Button
// Importa ButtonDefaults para customizar as propriedades padrão do botão (como paleta de cores)
import androidx.compose.material3.ButtonDefaults
// Importa HorizontalDivider para renderizar uma linha divisória horizontal fina entre seções da tela
import androidx.compose.material3.HorizontalDivider
// Importa Scaffold que fornece o esqueleto visual básico de tela conforme as diretrizes do Material 3
import androidx.compose.material3.Scaffold
// Importa Text para renderizar textos tipográficos com fontes, cores e pesos configuráveis
import androidx.compose.material3.Text
// Importa a anotação @Composable que transforma qualquer função comum em um componente declarativo de UI
import androidx.compose.runtime.Composable
// Importa getValue para permitir o operador de delegação 'by' ao fazer a leitura de variáveis de estado
import androidx.compose.runtime.getValue
// Importa mutableStateOf para criar um estado observável cujo valor notifica o Compose para redesenhar a UI
import androidx.compose.runtime.mutableStateOf
// Importa remember para fazer o Compose reter o valor de uma variável durante as recomposições da tela
import androidx.compose.runtime.remember
// Importa setValue para permitir o operador de delegação 'by' ao atribuir novos valores à variável de estado
import androidx.compose.runtime.setValue
// Importa Alignment para posicionar e alinhar itens no início, centro ou fim de caixas e layouts
import androidx.compose.ui.Alignment
// Importa Modifier, o objeto central do Compose para encadear modificações visuais e comportamentais
import androidx.compose.ui.Modifier
// Importa clip para aplicar uma máscara de corte geométrico no componente (ex: círculos, retângulos arredondados)
import androidx.compose.ui.draw.clip
// Importa Offset para definir pontos de coordenadas cartesianas bidimensionais (eixo X, eixo Y)
import androidx.compose.ui.geometry.Offset
// Importa Size para representar dimensões de largura (width) e altura (height) no desenho vetorial em Canvas
import androidx.compose.ui.geometry.Size
// Importa Color para definir cores em hexadecimal ou paletas com suporte a transparência (alpha)
import androidx.compose.ui.graphics.Color
// Importa StrokeCap para configurar o estilo de acabamento das pontas das linhas (arredondado ou reto)
import androidx.compose.ui.graphics.StrokeCap
// Importa Stroke para desenhar apenas o contorno vazado de formas geométricas sem preencher o miolo
import androidx.compose.ui.graphics.drawscope.Stroke
// Importa FontWeight para escolher o peso da fonte (Normal, Medium, SemiBold, Bold)
import androidx.compose.ui.text.font.FontWeight
// Importa TextAlign para configurar o alinhamento de blocos de texto (Center, Start, End, Justify)
import androidx.compose.ui.text.style.TextAlign
// Importa Preview para permitir a visualização gráfica do Composable diretamente na IDE Android Studio
import androidx.compose.ui.tooling.preview.Preview
// Importa a unidade 'dp' (Density-independent Pixels) usada para dimensionar elementos físicos na tela
import androidx.compose.ui.unit.dp
// Importa a unidade 'sp' (Scale-independent Pixels) usada exclusivamente para fontes e acessibilidade
import androidx.compose.ui.unit.sp
// Importa o tema visual TesteTheme configurado no arquivo Theme.kt da pasta ui/theme
import com.teamxx.teste.ui.theme.TesteTheme

// ========================================================================================
// 1. MODELO DE DADOS E ESTADO DA SPRINT 02 (SignalStatus)
// ========================================================================================

/**
 * Enumeração [SignalStatus]: representa cada um dos 4 possíveis estados da medição de sinal Wi-Fi.
 * Encapsula o valor numérico em dBm e todas as propriedades visuais associadas a cada nível.
 *
 * @param label Texto amigável exibido no chip de status abaixo do radar (ex: "Aguardando", "Sinal Forte").
 * @param dbm Valor numérico simulado em dBm (ou null quando nenhuma medição foi realizada ainda).
 * @param sectorColor Cor do setor de varredura iluminado desenhado no Canvas do radar.
 * @param textColor Cor temática do texto do chip, do valor em dBm e do ícone de Wi-Fi central.
 */
enum class SignalStatus(
    val label: String,       // Nome amigável do status para exibição direta na interface
    val dbm: Int?,           // Medição simulada da intensidade em decibéis-miliwatts (dBm)
    val sectorColor: Color,  // Cor de iluminação da fatia do radar em Canvas
    val textColor: Color     // Cor temática da tipografia informativa e do ícone central
) {
    // ------------------------------------------------------------------------------------
    // Estado Inicial: nenhuma medição foi feita ainda pelo usuário ("Aguardando" e "— DBM")
    // ------------------------------------------------------------------------------------
    NONE(
        label = "Aguardando",            // Rótulo neutro aguardando a primeira ação
        dbm = null,                      // Valor nulo representa que ainda não há leitura numérica
        sectorColor = Color.Transparent, // Cor transparente para não desenhar nenhum setor no radar
        textColor = Color(0xFF8FA3B8)    // Tom cinza-azulado suave indicando espera
    ),

    // ------------------------------------------------------------------------------------
    // Estado de Sinal Forte: conexão excelente com alta velocidade e baixa latência (-45 dBm)
    // ------------------------------------------------------------------------------------
    STRONG(
        label = "Sinal Forte",           // Rótulo informando excelente conexão
        dbm = -45,                       // -45 dBm é o padrão da indústria para sinal muito forte
        sectorColor = Color(0xFF4CAF50), // Verde vibrante para representar qualidade excelente
        textColor = Color(0xFF81C784)    // Verde claro suave para ótima legibilidade no fundo escuro
    ),

    // ------------------------------------------------------------------------------------
    // Estado de Sinal Médio: conexão intermediária aceitável para navegação comum (-68 dBm)
    // ------------------------------------------------------------------------------------
    MEDIUM(
        label = "Sinal Médio",           // Rótulo informando conexão moderada
        dbm = -68,                       // -68 dBm representa sinal mediano típico
        sectorColor = Color(0xFFFF9800), // Laranja/âmbar indicando estado de atenção
        textColor = Color(0xFFFFB74D)    // Laranja claro para manter contraste no modo noturno
    ),

    // ------------------------------------------------------------------------------------
    // Estado de Sinal Fraco: conexão degradada sujeita a oscilações e quedas (-85 dBm)
    // ------------------------------------------------------------------------------------
    WEAK(
        label = "Sinal Fraco",           // Rótulo alertando sobre conexão de baixa qualidade
        dbm = -85,                       // -85 dBm representa sinal muito fraco no limiar de desconexão
        sectorColor = Color(0xFFE53935), // Vermelho vivo para alertar problema de sinal
        textColor = Color(0xFFFF5252)    // Vermelho claro vibrante para máxima visibilidade
    )
}

// ========================================================================================
// 2. ACTIVITY PRINCIPAL DO ANDROID (Ponto de Entrada da Aplicação)
// ========================================================================================

/**
 * Classe [MainActivity]: primeira Activity instanciada pelo sistema operacional ao abrir o app.
 * Herda de [ComponentActivity], permitindo inflar a interface exclusivamente com Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

    // Método onCreate: primeiro gatilho disparado no ciclo de vida da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        // Executa a lógica de criação e inicialização da classe-mãe ComponentActivity
        super.onCreate(savedInstanceState)

        // Habilita o modo Edge-to-Edge para renderizar sob a barra de status e de navegação
        enableEdgeToEdge()

        // setContent: método que substitui o antigo setContentView(R.layout...) pelo Jetpack Compose
        setContent {
            // Envelopa a árvore de componentes com o tema customizado do aplicativo (TesteTheme)
            TesteTheme {
                // Scaffold: componente estrutural que fornece os limites e margens de tela do Material 3
                Scaffold(
                    // Configura o modificador para preencher toda a tela com o tom azul escuro da marca
                    modifier = Modifier
                        .fillMaxSize()                 // Preenche 100% da largura e altura do visor
                        .background(Color(0xFF0B111E)) // Cor de fundo azul noite escuro (#0B111E)
                ) { innerPadding ->
                    // innerPadding: objeto com as margens de respiro da barra de status e navegação
                    WifiMapperHomeScreen(
                        // Aplica o espaçamento seguro fornecido pelo Scaffold na Composable principal
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// ========================================================================================
// 3. TELA PRINCIPAL (Composable com Gerenciamento de Estado Reativo)
// ========================================================================================

/**
 * Função Composable [WifiMapperHomeScreen]: tela principal do Wi-Fi Mapper contendo cabeçalho,
 * radar com animação vetorial, chip de status e botão de ação primária reativo.
 *
 * @param modifier Modificador para repassar configurações de layout (como o innerPadding do Scaffold).
 */
@Composable
fun WifiMapperHomeScreen(modifier: Modifier = Modifier) {

    // ------------------------------------------------------------------------------------
    // VARIÁVEL DE ESTADO REATIVO (Coração da Sprint 02):
    // 1. 'remember': instrui o Compose a memorizar este valor e NÃO reiniciá-lo a cada recomposição.
    // 2. 'mutableStateOf': cria uma propriedade observável. Quando ela muda, a UI recomponha na hora.
    // 3. 'by': delegação de propriedade do Kotlin (permite ler/gravar direto sem usar .value).
    // ------------------------------------------------------------------------------------
    var signalStatus by remember { mutableStateOf(SignalStatus.NONE) }

    // Column: organiza todos os elementos verticais da tela (Header, Conteúdo e Botão)
    Column(
        modifier = modifier
            .fillMaxSize()                  // Estica a coluna para cobrir 100% do espaço disponível
            .background(Color(0xFF0B111E))  // Cor azul marinho escuro de fundo principal
    ) {

        // ================================================================================
        // SEÇÃO DO CABEÇALHO (Header com Logotipo, Nome do App e Subtítulo)
        // ================================================================================
        Row(
            modifier = Modifier
                .fillMaxWidth()                                 // Ocupa toda a largura da tela
                .padding(horizontal = 20.dp, vertical = 16.dp), // Margens laterais de 20dp e verticais de 16dp
            verticalAlignment = Alignment.CenterVertically      // Alinha verticalmente o ícone e os textos ao centro
        ) {
            // Box circular estilizado que funciona como moldura para o ícone de Wi-Fi
            Box(
                modifier = Modifier
                    .size(44.dp)                                      // Dimensão fixa de 44x44 dp
                    .clip(CircleShape)                                // Corta o container em círculo perfeito
                    .background(Color(0xFF142033))                    // Cor azul petróleo de fundo do card (#142033)
                    .border(1.dp, Color(0xFF1E304B), CircleShape),    // Borda circular fina de 1 dp (#1E304B)
                contentAlignment = Alignment.Center                   // Centraliza o ícone de Wi-Fi no centro do círculo
            ) {
                // Desenha o ícone vetorial de Wi-Fi na cor ciano neon característica do app
                WifiIcon(
                    color = Color(0xFF00E5FF),      // Cor ciano vibrante (#00E5FF)
                    modifier = Modifier.size(22.dp) // Tamanho de 22x22 dp para encaixar com folga no círculo
                )
            }

            // Espaçamento horizontal vazio de 14 dp entre o ícone circular e o bloco de textos
            Spacer(modifier = Modifier.width(14.dp))

            // Coluna interna que agrupa o título do produto e a categoria funcional
            Column {
                // Nome do Aplicativo (Requisito Funcional da Sprint 01 preservado)
                Text(
                    text = "Wi-Fi Mapper",         // Nome da marca do produto
                    fontSize = 17.sp,              // Tamanho de 17 sp elegante e legível
                    fontWeight = FontWeight.Bold,  // Negrito para destacar a identidade da marca
                    color = Color.White            // Cor branca para máximo contraste
                )
                // Subtítulo descritivo em letras maiúsculas (caixa alta)
                Text(
                    text = "SIGNAL DIAGNOSTICS",      // Rótulo da área de utilidade técnica
                    fontSize = 10.sp,                 // Tamanho compacto de 10 sp
                    fontWeight = FontWeight.SemiBold, // Semi-negrito para manter nitidez
                    color = Color(0xFF7E92A8),        // Cinza azulado suave (#7E92A8)
                    letterSpacing = 1.2.sp            // Espaçamento entre letras para estética moderna
                )
            }
        }

        // Linha divisória horizontal sutil de 1 dp separando o cabeçalho do restante do layout
        HorizontalDivider(
            color = Color(0xFF141F33), // Cor azul escuro sutil para não poluir visualmente (#141F33)
            thickness = 1.dp           // Espessura ultra-fina de 1 dp
        )

        // ================================================================================
        // SEÇÃO DE CONTEÚDO CENTRAL (Títulos, Radar Canvas, Chip Informativo e Botão)
        // ================================================================================
        Column(
            modifier = Modifier
                .fillMaxSize()                                  // Preenche todo o restante da altura da tela
                .padding(horizontal = 24.dp, vertical = 20.dp), // Margens laterais de 24dp e verticais de 20dp
            horizontalAlignment = Alignment.CenterHorizontally  // Centraliza todos os componentes no eixo horizontal
        ) {
            // Espaçador superior de 24 dp para afastar o título da linha do cabeçalho
            Spacer(modifier = Modifier.height(24.dp))

            // Título da funcionalidade em destaque
            Text(
                text = "Verificação de Sinal", // Ação que a tela realiza
                fontSize = 28.sp,              // Fonte grande de 28 sp para impacto visual imediato
                fontWeight = FontWeight.Bold,  // Peso negrito para estabelecer a hierarquia da tela
                color = Color.White,           // Cor branca pura para legibilidade máxima
                textAlign = TextAlign.Center   // Alinhado rigorosamente ao centro
            )

            // Espaçamento de 8 dp entre o título e o slogan
            Spacer(modifier = Modifier.height(8.dp))

            // Slogan / Proposta de valor do produto (Requisito da Sprint 01 preservado)
            Text(
                text = "Mapeie a rede. Conecte no melhor ponto.", // Frase de efeito que orienta o usuário
                fontSize = 15.sp,                                 // Tamanho equilibrado de 15 sp
                color = Color(0xFF00D2E5),                        // Ciano moderno de destaque visual (#00D2E5)
                textAlign = TextAlign.Center                      // Centralizado sob o título principal
            )

            // Espaçador generoso de 36 dp para separar o texto explicativo do radar
            Spacer(modifier = Modifier.height(36.dp))

            // ----------------------------------------------------------------------------
            // COMPONENTE DO RADAR GRÁFICO (Reage dinamicamente à variável 'signalStatus')
            // ----------------------------------------------------------------------------
            RadarView(
                status = signalStatus,           // Repassa o estado atual para o radar redesenhar
                modifier = Modifier.size(270.dp) // Define a área quadrada do radar como 270x270 dp
            )

            // Espaçador de 28 dp entre o radar e o chip de status qualitativo
            Spacer(modifier = Modifier.height(28.dp))

            // ----------------------------------------------------------------------------
            // CHIP DE STATUS (Pílula arredondada que exibe o rótulo e a cor do estado)
            // ----------------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))                      // Corta as bordas em formato de pílula (50%)
                    .background(Color(0xFF131C2D))                     // Fundo escuro azulado (#131C2D)
                    .padding(horizontal = 24.dp, vertical = 10.dp),    // Padding interno (respiro do texto)
                contentAlignment = Alignment.Center                    // Centraliza o texto dentro do Box
            ) {
                // Texto reativo que exibe: "Aguardando", "Sinal Forte", "Sinal Médio" ou "Sinal Fraco"
                Text(
                    text = signalStatus.label,       // Lê dinamicamente o rótulo do status atual
                    color = signalStatus.textColor,  // Aplica a cor temática definida no enum (verde, laranja, etc.)
                    fontSize = 14.sp,                // Tamanho padrão de 14 sp
                    fontWeight = FontWeight.Medium   // Peso médio para leitura confortável
                )
            }

            // Spacer com peso flexível (weight = 1f): empurra o botão para a parte inferior da tela
            Spacer(modifier = Modifier.weight(1f))

            // ----------------------------------------------------------------------------
            // BOTÃO DE AÇÃO PRIMÁRIA (Gatilho de Evento que Atualiza o Estado Reativo)
            // ----------------------------------------------------------------------------
            Button(
                // onClick: bloco de código executado quando o usuário toca no botão
                onClick = {
                    // Lista contendo os três possíveis estados pós-medição
                    val availableStatuses = listOf(
                        SignalStatus.STRONG, // Sinal forte (-45 dBm)
                        SignalStatus.MEDIUM, // Sinal médio (-68 dBm)
                        SignalStatus.WEAK    // Sinal fraco (-85 dBm)
                    )
                    // Sorteia aleatoriamente um dos 3 status ativos e atribui à variável reativa
                    // A atribuição dispara a recomposição instantânea de todos os elementos que leem signalStatus!
                    signalStatus = availableStatuses.random()
                },
                // Define o arredondamento dos cantos do botão com 14 dp
                shape = RoundedCornerShape(14.dp),
                // Customização das cores do botão do Material 3
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E5FF), // Fundo em ciano elétrico (#00E5FF)
                    contentColor = Color.Black          // Conteúdo (texto e ícone) em preto para alto contraste
                ),
                modifier = Modifier
                    .fillMaxWidth()   // Faz o botão se estender por toda a largura disponível
                    .height(56.dp)    // Altura ergonômica recomendada de 56 dp para facilidade de toque
            ) {
                // Row interna do botão para alinhar horizontalmente o ícone de Play e o texto
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Alinha verticalmente ao centro da barra
                    horizontalArrangement = Arrangement.Center      // Centraliza os dois itens horizontalmente
                ) {
                    // Ícone de triângulo Play em formato de caractere tipográfico
                    Text(
                        text = "▶",                   // Símbolo de ação/início
                        fontSize = 14.sp,              // Tamanho de 14 sp
                        fontWeight = FontWeight.Bold,  // Negrito para espessura compatível com o texto
                        color = Color.Black            // Cor preta para contraste perfeito
                    )
                    // Espaço horizontal de 8 dp entre o triângulo e a descrição da ação
                    Spacer(modifier = Modifier.width(8.dp))
                    // Texto com o rótulo da ação do botão
                    Text(
                        text = "Iniciar Verificação",  // Frase imperativa que orienta o usuário
                        fontSize = 16.sp,              // Tamanho de 16 sp legível
                        fontWeight = FontWeight.Bold,  // Negrito para demonstrar primazia da ação
                        color = Color.Black            // Cor preta
                    )
                }
            }

            // Espaçamento inferior de 16 dp para garantir respiro acima da barra de navegação
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ========================================================================================
// 4. RADAR EM CANVAS (Desenho Vetorial Geométrico Reativo)
// ========================================================================================

/**
 * Função Composable [RadarView]: constrói visualmente o radar circular através de primitivas gráficas
 * vetoriais em Canvas (círculos concêntricos, retas ortogonais e setor circular colorido).
 *
 * @param status O estado atual da medição (determina a cor do setor e os dados do centro).
 * @param modifier Modificador para customização de dimensões e posicionamento.
 */
@Composable
fun RadarView(
    status: SignalStatus,          // Recebe o status atual para reagir visualmente
    modifier: Modifier = Modifier  // Modificador padrão com valor default
) {
    // Box permite sobrepor em camadas: no fundo o Canvas vetorial e na frente o texto central
    Box(
        modifier = modifier,                // Aplica o tamanho passado (270x270 dp)
        contentAlignment = Alignment.Center // Centraliza todos os elementos no centro do Box
    ) {
        // Canvas: área de desenho livre fornecida pelo Compose que roda comandos gráficos de baixo nível
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Calcula o ponto de coordenada central (X=metade da largura, Y=metade da altura)
            val centerOffset = Offset(size.width / 2f, size.height / 2f)
            // Calcula o raio máximo do radar (igual à metade da largura da área de desenho)
            val maxRadius = size.width / 2f
            // Define a cor azul petróleo escuro para as linhas da malha do radar (#1E2D44)
            val gridColor = Color(0xFF1E2D44)
            // Converte a medida de espessura de 1.5 dp para pixels reais da tela do dispositivo
            val strokeWidth = 1.5.dp.toPx()

            // ----------------------------------------------------------------------------
            // 1. SETOR CIRCULAR COLORIDO (Fatia iluminada em arco de pizza)
            // Desenhado SOMENTE quando houver uma medição ativa (diferente de SignalStatus.NONE)
            // ----------------------------------------------------------------------------
            if (status != SignalStatus.NONE) {
                drawArc(
                    color = status.sectorColor.copy(alpha = 0.35f), // Aplica transparência de 35% na cor
                    startAngle = -90f,                             // Ponto inicial: -90° (topo, posição 12 horas)
                    sweepAngle = 60f,                              // Abertura angular: varre 60 graus no sentido horário
                    useCenter = true,                              // Fecha as duas pontas no centro para formar a fatia
                    topLeft = Offset(0f, 0f),                      // Canto superior esquerdo do quadrado delimitador
                    size = size                                    // Ocupa todas as dimensões do Canvas
                )
            }

            // ----------------------------------------------------------------------------
            // 2. TRÊS CÍRCULOS CONCÊNTRICOS DA MALHA DO RADAR
            // ----------------------------------------------------------------------------
            // Círculo concêntrico externo (raio máximo de 100%)
            drawCircle(
                color = gridColor,                  // Cor da linha de grade (#1E2D44)
                radius = maxRadius,                 // Raio cobrindo 100% até a borda
                center = centerOffset,              // Posicionado exatamente no centro (X, Y)
                style = Stroke(width = strokeWidth) // Desenha apenas o contorno vazado
            )
            // Círculo concêntrico intermediário (raio de 68% do raio total)
            drawCircle(
                color = gridColor,
                radius = maxRadius * 0.68f,         // 68% da distância até a borda
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )
            // Círculo concêntrico interno (raio de 36% do raio total)
            drawCircle(
                color = gridColor,
                radius = maxRadius * 0.36f,         // 36% da distância até a borda
                center = centerOffset,
                style = Stroke(width = strokeWidth)
            )

            // ----------------------------------------------------------------------------
            // 3. RETAS DE MIRA ORTOGONAIS (Cruz central de coordenadas)
            // ----------------------------------------------------------------------------
            // Linha reta horizontal que atravessa o radar de ponta a ponta
            drawLine(
                color = gridColor,                         // Mesma cor da grade
                start = Offset(0f, centerOffset.y),        // Começa na borda esquerda (X=0, Y=centro)
                end = Offset(size.width, centerOffset.y),  // Termina na borda direita (X=largura, Y=centro)
                strokeWidth = strokeWidth                  // Espessura idêntica à dos círculos
            )
            // Linha reta vertical que atravessa o radar de cima a baixo
            drawLine(
                color = gridColor,                          // Mesma cor da grade
                start = Offset(centerOffset.x, 0f),         // Começa no topo (X=centro, Y=0)
                end = Offset(centerOffset.x, size.height),  // Termina na base (X=centro, Y=altura)
                strokeWidth = strokeWidth                   // Espessura idêntica à dos círculos
            )
        }

        // ================================================================================
        // 4. ELEMENTOS CENTRALIZADOS NO MIOLO DO RADAR (Ícone, Valor dBm e Unidade)
        // ================================================================================
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Alinha todos os textos e ícone ao centro
            verticalArrangement = Arrangement.Center            // Centraliza o bloco na altura do miolo do radar
        ) {
            // Ícone vetorial de Wi-Fi menor, cuja cor reflete a cor temática do status medido
            WifiIcon(
                color = status.textColor,       // Cor reativa (cinza, verde, laranja ou vermelho)
                modifier = Modifier.size(24.dp) // Dimensão de 24x24 dp
            )

            // Espaçamento vertical de 4 dp entre o ícone e o número em dBm
            Spacer(modifier = Modifier.height(4.dp))

            // Exibição do valor em dBm: exibe o número medido (ex: "-85") ou o traço "—" quando em espera
            Text(
                text = if (status.dbm != null) "${status.dbm}" else "—", // Condicional ternária de exibição
                fontSize = 26.sp,                                        // Tamanho proeminente de 26 sp
                fontWeight = FontWeight.Bold,                            // Negrito para leitura rápida e clara
                color = Color.White                                      // Cor branca com alto contraste
            )

            // Rótulo descritivo da unidade técnica de medida de sinal de radiofrequência
            Text(
                text = "DBM",                     // Sigla de Decibéis-miliwatts
                fontSize = 11.sp,                 // Tamanho compacto de 11 sp
                fontWeight = FontWeight.SemiBold, // Semi-negrito para ótima nitidez
                color = Color(0xFF7E92A8),        // Cinza azulado para indicar legenda secundária
                letterSpacing = 1.sp              // Espaçamento estilizado entre os caracteres
            )
        }
    }
}

// ========================================================================================
// 5. DESENHO VETORIAL DO ÍCONE DE WI-FI EM CANVAS
// ========================================================================================

/**
 * Função Composable [WifiIcon]: desenha o símbolo oficial de Wi-Fi a partir de comandos matemáticos
 * de Canvas (dois arcos concêntricos representando as ondas e um círculo representando a antena base).
 *
 * @param color Cor utilizada para traçar as ondas e o ponto transmissor.
 * @param modifier Modificador para customização de tamanho do ícone.
 */
@Composable
fun WifiIcon(
    color: Color,                  // Cor dinâmica recebida por parâmetro
    modifier: Modifier = Modifier  // Modificador de tamanho e layout
) {
    // Área de desenho vetorial Canvas do ícone
    Canvas(modifier = modifier) {
        // Calcula a espessura das linhas proporcionalmente (12% da largura total do componente)
        val strokeWidth = size.width * 0.12f
        // Guarda a largura total do Canvas
        val w = size.width
        // Guarda a altura total do Canvas
        val h = size.height

        // --------------------------------------------------------------------------------
        // ONDA SUPERIOR DE TRANSMISSÃO (Arco externo mais aberto)
        // --------------------------------------------------------------------------------
        drawArc(
            color = color,                                              // Cor dinâmica repassada
            startAngle = 205f,                                          // Ângulo inicial em 205 graus
            sweepAngle = 130f,                                          // Abertura angular de 130 graus
            useCenter = false,                                          // Desenho aberto (não liga as pontas ao centro)
            topLeft = Offset(0f, h * 0.12f),                            // Deslocamento superior no eixo Y
            size = Size(w, h * 0.95f),                                  // Proporção da elipse do arco
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)  // Borda com pontas suavemente arredondadas
        )

        // --------------------------------------------------------------------------------
        // ONDA INTERMEDIÁRIA DE TRANSMISSÃO (Arco concêntrico médio)
        // --------------------------------------------------------------------------------
        drawArc(
            color = color,
            startAngle = 205f,                                          // Mesmo ângulo inicial para manter simetria
            sweepAngle = 130f,                                          // Mesma abertura angular das ondas
            useCenter = false,
            topLeft = Offset(w * 0.22f, h * 0.38f),                     // Deslocamento interno proporcional
            size = Size(w * 0.56f, h * 0.65f),                          // Dimensão reduzida da elipse
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)  // Pontas arredondadas idênticas
        )

        // --------------------------------------------------------------------------------
        // PONTO DE ORIGEM DA ANTENA TRANSMISSORA (Círculo na base)
        // --------------------------------------------------------------------------------
        drawCircle(
            color = color,                       // Mesma cor temática do ícone
            radius = strokeWidth * 0.85f,        // Raio proporcional à espessura das ondas
            center = Offset(w * 0.5f, h * 0.84f) // Coordenada central no terço inferior da base (X=50%, Y=84%)
        )
    }
}

// ========================================================================================
// 6. PRÉ-VISUALIZAÇÃO DO COMPOSE NA IDE (Android Studio Preview)
// ========================================================================================

/**
 * Função [WifiMapperHomeScreenPreview]: renderiza o preview interativo diretamente na aba Split/Design
 * do Android Studio sem precisar compilar no emulador.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WifiMapperHomeScreenPreview() {
    // Aplica o tema visual TesteTheme para exibir cores e tipografias oficiais no editor
    TesteTheme {
        // Chama a tela principal para renderizar o layout completo no preview
        WifiMapperHomeScreen()
    }
}