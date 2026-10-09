package com.teamxx.teste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teamxx.teste.ui.theme.TesteTheme

/**
 * [MainActivity]: ponto de entrada principal do aplicativo Android.
 * Inicializa a navegação declarativa com Jetpack Compose via [NavHost].
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita renderização edge-to-edge sob as barras do sistema
        enableEdgeToEdge()

        setContent {
            TesteTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0B111E))
                ) { innerPadding ->
                    // Componente raiz que orquestra a navegação e o estado do app
                    WifiMapperApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * [WifiMapperApp]: componente raiz responsável por gerenciar o grafo de navegação
 * ([NavHost]) e reter o estado de medição do sinal ([signalStatus]) entre as telas.
 *
 * @param navController Controlador de navegação do Compose.
 * @param modifier Modificador de layout repassado pelo Scaffold.
 */
@Composable
fun WifiMapperApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    // Estado de medição lembrado com rememberSaveable para manter o resultado
    // mesmo após navegar para a tela de detalhes e voltar à tela inicial
    var signalStatus by rememberSaveable { mutableStateOf(SignalStatus.NONE) }

    // Grafo de navegação conectando as telas do produto
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        // ----------------------------------------------------------------------------
        // ROTA 1: "home" — Tela principal de verificação e radar de sinal
        // ----------------------------------------------------------------------------
        composable(route = "home") {
            WifiMapperHomeScreen(
                status = signalStatus,
                onStatusChange = { newStatus ->
                    signalStatus = newStatus
                },
                onNavigateToDetails = { status ->
                    // Navega para a tela de detalhes passando o nome do status como argumento
                    navController.navigate("details/${status.name}")
                }
            )
        }

        // ----------------------------------------------------------------------------
        // ROTA 2: "details/{status}" — Tela secundária com diagnóstico detalhado
        // ----------------------------------------------------------------------------
        composable(route = "details/{status}") { backStackEntry ->
            // Recupera o argumento da rota e resolve para a constante do enum
            val statusParam = backStackEntry.arguments?.getString("status")
            val currentStatus = SignalStatus.fromName(statusParam)

            DetailsScreen(
                status = currentStatus,
                onBack = {
                    // Desempilha a tela atual e retorna para a tela inicial
                    navController.popBackStack()
                }
            )
        }
    }
}