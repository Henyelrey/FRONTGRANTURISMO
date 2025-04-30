package pe.edu.upeu.granturismojpc.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pe.edu.upeu.granturismojpc.ui.presentation.screens.Pantalla1
import pe.edu.upeu.granturismojpc.ui.presentation.screens.Pantalla2
import pe.edu.upeu.granturismojpc.ui.presentation.screens.Pantalla3
import pe.edu.upeu.granturismojpc.ui.presentation.screens.Pantalla4
import pe.edu.upeu.granturismojpc.ui.presentation.screens.Pantalla5
import pe.edu.upeu.granturismojpc.ui.presentation.screens.home.HomeScreen
import pe.edu.upeu.granturismojpc.ui.presentation.screens.home.HomeViewModel
import pe.edu.upeu.granturismojpc.ui.presentation.screens.login.LoginScreen
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteForm
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteMain
import pe.edu.upeu.granturismojpc.ui.presentation.screens.proveedor.ProveedorForm
import pe.edu.upeu.granturismojpc.ui.presentation.screens.proveedor.ProveedorMain
import pe.edu.upeu.granturismojpc.ui.presentation.screens.register.RegisterScreen
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicio.ServicioForm
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicio.ServicioMain
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioalimentacion.ServicioAlimentacionForm
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioalimentacion.ServicioAlimentacionMain
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioartesania.ServicioArtesaniaForm
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioartesania.ServicioArtesaniaMain
import pe.edu.upeu.granturismojpc.ui.presentation.screens.serviciohotelera.ServicioHoteleraForm
import pe.edu.upeu.granturismojpc.ui.presentation.screens.serviciohotelera.ServicioHoteleraMain

@Composable
fun NavigationHost(
    navController: NavHostController,
    darkMode: MutableState<Boolean>,
    modif: PaddingValues
) {
    NavHost(
        navController = navController, startDestination =
            Destinations.Login.route
    ) {
        composable(Destinations.Login.route) {
            LoginScreen(
                navigateToHome = { navController.navigate(Destinations.HomeScreen.route) },
                navigateToRegisterScreen = { navController.navigate(Destinations.Register.route) }
            )
        }

        composable(Destinations.Register.route) {
            RegisterScreen(
                navigateToLogin = { navController.navigate(Destinations.Login.route) },
                navigateToHome = { navController.navigate(Destinations.Pantalla1.route) },
            )
        }

        composable(Destinations.Pantalla1.route) {
            Pantalla1(navegarPantalla2 = { newText ->
                navController.navigate(
                    Destinations.Pantalla2.createRoute(
                        newText
                    )
                )
            }
            )
        }

        composable(
            Destinations.Pantalla2.route, arguments = listOf(navArgument("newText") {
                defaultValue = "Pantalla 2"
            })
        ) { navBackStackEntry ->
            var newText = navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
            Pantalla2(newText, darkMode)
        }

        composable(Destinations.Pantalla3.route) {
            Pantalla3()
        }
        composable(Destinations.Pantalla4.route) {
            Pantalla4()
        }
        composable(Destinations.Pantalla5.route) {
            Pantalla5()
        }

        composable(Destinations.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                navegarPantalla2 = { newText ->
                    navController.navigate(
                        Destinations.Pantalla2.createRoute(newText)
                    )
                },
                navController = navController,
                viewModel = viewModel
            )
        }


        composable(Destinations.PaqueteMainSC.route){
            PaqueteMain(navegarEditarAct = {newText->
                navController.navigate(Destinations.PaqueteFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.PaqueteFormSC.route, arguments =
            listOf(navArgument("prodId"){
                defaultValue="prodId"
            })){navBackStackEntry -> var
                prodId=navBackStackEntry.arguments?.getString("prodId")
            requireNotNull(prodId)
            PaqueteForm(text = prodId, darkMode = darkMode,
                navController=navController )
        }

        composable(Destinations.ProveedorMainSC.route){
            ProveedorMain(navegarEditarAct = {newText->
                navController.navigate(Destinations.ProveedorFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.ProveedorFormSC.route, arguments =
            listOf(navArgument("provId"){
                defaultValue="provId"
            })){navBackStackEntry -> var
                provId=navBackStackEntry.arguments?.getString("provId")
            requireNotNull(provId)
            ProveedorForm(text = provId, darkMode = darkMode,
                navController=navController )
        }

        composable(Destinations.ServicioMainSC.route){
            ServicioMain(navegarEditarAct = {newText->
                navController.navigate(Destinations.ServicioFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.ServicioFormSC.route, arguments =
            listOf(navArgument("servId"){
                defaultValue="servId"
            })){navBackStackEntry -> var
                servId=navBackStackEntry.arguments?.getString("servId")
            requireNotNull(servId)
            ServicioForm(text = servId, darkMode = darkMode,
                navController=navController )
        }

        composable(Destinations.ServicioAlimentacionMainSC.route){
            ServicioAlimentacionMain(navegarEditarAct = {newText->
                navController.navigate(Destinations.ServicioAlimentacionFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.ServicioAlimentacionFormSC.route, arguments =
            listOf(navArgument("servaliId"){
                defaultValue="servaliId"
            })){navBackStackEntry -> var
                servaliId=navBackStackEntry.arguments?.getString("servaliId")
            requireNotNull(servaliId)
            ServicioAlimentacionForm(text = servaliId, darkMode = darkMode,
                navController=navController )
        }

        composable(Destinations.ServicioArtesaniaMainSC.route){
            ServicioArtesaniaMain(navegarEditarAct = {newText->
                navController.navigate(Destinations.ServicioArtesaniaFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.ServicioArtesaniaFormSC.route, arguments =
            listOf(navArgument("servartId"){
                defaultValue="servartId"
            })){navBackStackEntry -> var
                servartId=navBackStackEntry.arguments?.getString("servartId")
            requireNotNull(servartId)
            ServicioArtesaniaForm(text = servartId, darkMode = darkMode,
                navController=navController )
        }

        composable(Destinations.ServicioHoteleraMainSC.route){
            ServicioHoteleraMain(navegarEditarAct = {newText->
                navController.navigate(Destinations.ServicioHoteleraFormSC.passId(newText))},
                navController =navController )
        }
        composable(Destinations.ServicioHoteleraFormSC.route, arguments =
            listOf(navArgument("servhotId"){
                defaultValue="servhotId"
            })){navBackStackEntry -> var
                servhotId=navBackStackEntry.arguments?.getString("servhotId")
            requireNotNull(servhotId)
            ServicioHoteleraForm(text = servhotId, darkMode = darkMode,
                navController=navController )
        }
    }
}