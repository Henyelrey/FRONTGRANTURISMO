package pe.edu.upeu.granturismojpc.ui.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.edu.upeu.granturismojpc.ui.navigation.Destinations
import pe.edu.upeu.granturismojpc.ui.presentation.screens.actividaddetalle.ActividadDetalleMainViewModel
import pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete.PaqueteMainViewModel

@Composable
fun Actividades(
    idPaquete: Long,
    viewModel: ActividadDetalleMainViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val actividades by viewModel.details.collectAsState()

    // Llamada para cargar actividades filtradas
    LaunchedEffect(idPaquete) {
        viewModel.obtenerActividadesPorPaquete(idPaquete)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Actividades del Paquete $idPaquete", style = MaterialTheme.typography.headlineSmall)

            if (actividades.isEmpty()) {
                Text("No hay actividades registradas para este paquete.")
            } else {
                actividades.forEach { actividad ->
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Título: ${actividad.titulo}", fontWeight = FontWeight.Bold)
                        Text("Descripción: ${actividad.descripcion}")
                    }
                }
            }
        }
    }
}
