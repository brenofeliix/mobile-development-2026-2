package com.team03.wifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team03.wifi.data.HistoryViewModel
import com.team03.wifi.data.MeasureViewModel
import com.team03.wifi.ui.theme.WifiTheme

// Rotas de navegação (Sprint 03; Sprint 06 adicionou o dbm real na rota)
private const val ROUTE_HOME = "home"
private const val ROUTE_DETAILS = "details/{status}/{dbm}"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WifiTheme {
                // Sprint 05: histórico no banco Room
                val historyViewModel: HistoryViewModel = viewModel()
                val history by historyViewModel.history.collectAsState()

                // Sprint 06: medição real do Wi-Fi
                val measureViewModel: MeasureViewModel = viewModel()
                val measureState by measureViewModel.state.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(ROUTE_HOME) {
                            HomeScreen(
                                onSeeDetails = { status, dbm ->
                                    navController.navigate("details/${status.name}/$dbm")
                                },
                                history = history,
                                measureState = measureState,
                                onMeasure = { roomName ->
                                    // Mede e, se der certo, salva no banco
                                    measureViewModel.measure { status ->
                                        historyViewModel.save(roomName, status.name)
                                    }
                                }
                            )
                        }

                        composable(
                            route = ROUTE_DETAILS,
                            arguments = listOf(
                                navArgument("status") { type = NavType.StringType },
                                navArgument("dbm") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val statusName = backStackEntry.arguments?.getString("status") ?: SignalStatus.NONE.name
                            val status = SignalStatus.valueOf(statusName)
                            val dbm = backStackEntry.arguments?.getInt("dbm") ?: 0

                            DetailsScreen(
                                status = status,
                                dbm = dbm,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}