package pe.edu.upeu.granturismojpc.ui.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteMainViewModel
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteMainViewModel_Factory


@Composable
fun DetalleScreen(
    idPaquete: Long,
    viewModel: PaqueteMainViewModel = hiltViewModel()

) {
    // Observa el paquete desde el ViewModel
    val paqueteState = viewModel.buscarPorId(idPaquete).collectAsState(initial = null)

    // Interfaz simple
    Column(modifier = Modifier
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
            }
        }
    }
}


