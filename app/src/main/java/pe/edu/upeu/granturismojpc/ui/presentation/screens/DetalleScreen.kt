package pe.edu.upeu.granturismojpc.ui.presentation.screens


import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
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
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.style.expressions.dsl.generated.zoom

//importaciones mapbox:

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style



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
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
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
                            Row {
                                Text(text = "${paquete.proveedor.nombreCompleto}")
                                Text(text = " - ${paquete.estado}")
                                Text(text = " - ${paquete.duracionDias} dias")
                            }
                            Text(text = "${paquete.descripcion}")
                            Text(text = "Preciio: ${paquete.precioTotal}")



                            Row {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "",
                                    tint = Color.Black,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = "${paquete.destino.ubicacion}")
                            }
                            Row {
                                Icon(
                                    imageVector = Icons.Filled.Person4,
                                    contentDescription = "",
                                    tint = Color.Black,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = "${paquete.cuposMaximos} personas")
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Ubicación",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp, end = 20.dp)
                )
            }
            item {
                MapScreen()
            }

            item {
                LazyRow {
                    item {
                        Text(
                            text = "Ver las actividades",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(top = 8.dp, start = 20.dp, end = 20.dp)
                        )

                        Button(
                            onClick = {
                                navController
                            },
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFA726) // Color verde de WhatsApp
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowForward, // Puedes usar otro ícono si deseas
                                contentDescription = "Actividades",
                                tint = Color.Black
                            )
                        }

                    }



                }
            }



        }
    }
}

@Composable
fun MapScreen() {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
                    getMapboxMap().setCamera(
                        CameraOptions.Builder()
                            .center(Point.fromLngLat(-70.246274, -15.500284)) // Ubicación de Puno, Perú
                            .zoom(12.0)
                            .build()
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    )
}

