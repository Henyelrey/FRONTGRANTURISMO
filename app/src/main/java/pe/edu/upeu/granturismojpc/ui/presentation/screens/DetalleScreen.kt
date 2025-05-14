package pe.edu.upeu.granturismojpc.ui.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import pe.edu.upeu.granturismojpc.R
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import pe.edu.upeu.granturismojpc.ui.presentation.components.CategoryChips
import pe.edu.upeu.granturismojpc.ui.presentation.components.HeroSection
import pe.edu.upeu.granturismojpc.ui.presentation.components.PaqueteCard
import pe.edu.upeu.granturismojpc.ui.presentation.components.SimpleBottomNavigationBar
import pe.edu.upeu.granturismojpc.ui.presentation.components.TopBar
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteMainViewModel
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteMainViewModel_Factory
import coil.compose.AsyncImage


@Composable
fun DetalleScreen(
    idPaquete: Long,
    viewModel: PaqueteMainViewModel = hiltViewModel(),
    navController: NavHostController,


) {
    // Observa el paquete desde el ViewModel
    val paqueteState = viewModel.buscarPorId(idPaquete).collectAsState(initial = null)

    //Valores
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = { println("Abriendo diálogo de filtros...") }
    val displaySnackBar = { println("Mostrando snackbar...") }
    val searchQuery = remember { mutableStateOf("") }





    // Interfaz simple
    /*Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        val paquete = paqueteState.value // Accedemos al valor real

        when {
            paquete == null -> {
                // Mientras se carga o si es null
                CircularProgressIndicator()
                Text("Cargando paquete...")
            }
            else -> {
                // Mostrar la información del paquete
                Text(text = "Título: ${paquete.titulo}", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Descripción: ${paquete.descripcion}")
                Text(text = "Precio: S/ ${paquete.precioTotal}")
                Text(text = "Descripcion: ${paquete.descripcion}")
                Text(text = "Imagen: ${paquete.imagenUrl}")
            }
        }
    }*/


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

        val paquete = paqueteState.value

        // Usar LazyColumn en lugar de Column con verticalScroll
        LazyColumn(


            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Aplicar el padding del Scaffold
        ) {
            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp) // un poco más de altura para dejar espacio a la barra
                ) {
                    // Imagen de fondo
                    AsyncImage(
                        model = paquete?.imagenUrl, // Asegúrate de que sea una URL válida desde Cloudinary
                        contentDescription = "Imagen del paquete turístico",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    // Gradiente
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.6f)
                                    ),
                                    startY = 100f
                                )
                            )
                    )

                    // Texto
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 56.dp
                            ) // deja espacio para la barra
                    ) {
                        when {
                            paquete == null -> {
                                CircularProgressIndicator()
                                Text("Cargando paquete...")
                            }

                            else -> {
                                Text(
                                    text = " ${paquete.titulo}",
                                    style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                )

                            }
                        }
                    }
                }
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
                    text = "Descripción",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp, end = 20.dp)
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    when {
                        paquete == null -> {
                            CircularProgressIndicator()
                            Text("Cargando paquete...")
                        }

                        else -> {
                            Text(text = " ${paquete.titulo}", fontSize = 24.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Descripción: ${paquete.descripcion}")
                            Text(text = "Precio: S/ ${paquete.precioTotal}")
                            Text(text = "Imagen: ${paquete.imagenUrl}")
                        }
                    }
                }
            }







        }
    }
}


