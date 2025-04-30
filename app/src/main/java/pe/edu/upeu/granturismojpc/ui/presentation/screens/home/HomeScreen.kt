package pe.edu.upeu.granturismojpc.ui.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import pe.edu.upeu.granturismojpc.ui.presentation.components.HeroSection
import pe.edu.upeu.granturismojpc.ui.presentation.components.TopBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import pe.edu.upeu.granturismojpc.ui.presentation.components.CategoryChips
import pe.edu.upeu.granturismojpc.ui.presentation.components.PaqueteCard


import pe.edu.upeu.granturismojpc.ui.presentation.components.SimpleBottomNavigationBar

@Composable
fun HomeScreen(
    navegarPantalla2: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,


    ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = { println("Abriendo diálogo de filtros...") }
    val displaySnackBar = { println("Mostrando snackbar...") }
    val searchQuery = remember { mutableStateOf("") }
    val paquetes by viewModel.prods.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.cargarPaquetes()
    }



    Scaffold(
        topBar = {
            TopBar(
                scope = scope,
                scaffoldState = drawerState,
                openDialog = openDialog,
                displaySnackBar = displaySnackBar
            )
        },
        bottomBar = {
            SimpleBottomNavigationBar(navController = navController)


        }
    ) { innerPadding ->
        // Usar LazyColumn en lugar de Column con verticalScroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Aplicar el padding del Scaffold
        ) {
            item {
                HeroSection(
                    searchQuery = searchQuery.value,
                    onSearchChange = { searchQuery.value = it }
                )
            }

            item {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Buscar asociaciones") },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
            }
            item {
                CategoryChips(navController = navController)
            }

            item {
                Text(
                    text = "Asociaciones Turisticas",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp, end = 20.dp)
                )
            }

            // Muestra los paquetes
            itemsIndexed (paquetes) { _, paquete ->
                PaqueteCard(paquete = paquete)
            }



        }
    }
}



