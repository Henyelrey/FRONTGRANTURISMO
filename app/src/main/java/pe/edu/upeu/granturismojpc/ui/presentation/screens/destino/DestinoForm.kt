package pe.edu.upeu.granturismojpc.ui.presentation.screens.destino

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.google.gson.Gson
import pe.edu.upeu.granturismojpc.model.ComboModel
import pe.edu.upeu.granturismojpc.model.DestinoDto
import pe.edu.upeu.granturismojpc.model.toDto
import pe.edu.upeu.granturismojpc.ui.navigation.Destinations
import pe.edu.upeu.granturismojpc.ui.presentation.components.Spacer
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.*
import pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioalimentacion.splitCadena

@Composable
fun DestinoForm(
    text: String,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: DestinoFormViewModel = hiltViewModel()
) {
    val destino by viewModel.destino.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var destinoD: DestinoDto
    if (text != "0") {
        destinoD = Gson().fromJson(text, DestinoDto::class.java)
        LaunchedEffect(Unit) {
            viewModel.getDestino(destinoD.idDestino)
        }
        destino?.let {
            destinoD = it.toDto()
            Log.i("DestinoX", "Destino: ${destinoD.toString()}")
        }
    } else {
        destinoD = DestinoDto(0, "", "", "", "", 0.0, 0.0, 0, 0.0, 0.0)
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    formularioDestino(
        id = destinoD.idDestino,
        darkMode = darkMode,
        navController = navController,
        destino = destinoD,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun formularioDestino(
    id: Long,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    destino: DestinoDto,
    viewModel: DestinoFormViewModel
) {
    val destinoForm = DestinoDto(0, "", "", "", "", 0.0, 0.0, 0, 0.0, 0.0)

    Scaffold(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        BuildEasyForms { easyForm ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                NameTextField(easyForms = easyForm, text = destino.nombre, "Nombre", MyFormKeys.NAME)
                NameTextField(easyForms = easyForm, text = destino.descripcion, "Descripción", MyFormKeys.DESCRIPTION)
                NameTextField(easyForms = easyForm, text = destino.ubicacion, "Ubicación", MyFormKeys.UTILIDAD)
                NameTextField(easyForms = easyForm, text = destino.imagenUrl, "Imagen URL", MyFormKeys.URL)
                NameTextField(easyForms = easyForm, text = destino.latitud.toString(), "Latitud", MyFormKeys.PU)
                NameTextField(easyForms = easyForm, text = destino.longitud.toString(), "Longitud", MyFormKeys.PU_OLD)
                NameTextField(easyForms = easyForm, text = destino.popularidad.toString(), "Popularidad", MyFormKeys.STOCK)
                NameTextField(easyForms = easyForm, text = destino.precioMedio.toString(), "Precio Medio", MyFormKeys.SLIDER)
                NameTextField(easyForms = easyForm, text = destino.rating.toString(), "Rating", MyFormKeys.RANGE_SLIDER)

                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    AccionButtonSuccess(easyForms = easyForm, "Guardar", id) {
                        val lista = easyForm.formData()
                        val nuevoDestino = DestinoDto(
                            idDestino = if (id != 0L) id else 0,
                            nombre = (lista[0] as EasyFormsResult.StringResult).value,
                            descripcion = (lista[1] as EasyFormsResult.StringResult).value,
                            ubicacion = (lista[2] as EasyFormsResult.StringResult).value,
                            imagenUrl = (lista[3] as EasyFormsResult.StringResult).value,
                            latitud = (lista[4] as EasyFormsResult.StringResult).value.toDoubleOrNull() ?: 0.0,
                            longitud = (lista[5] as EasyFormsResult.StringResult).value.toDoubleOrNull() ?: 0.0,
                            popularidad = (lista[6] as EasyFormsResult.StringResult).value.toIntOrNull() ?: 0,
                            precioMedio = (lista[7] as EasyFormsResult.StringResult).value.toDoubleOrNull() ?: 0.0,
                            rating = (lista[8] as EasyFormsResult.StringResult).value.toDoubleOrNull() ?: 0.0
                        )

                        if (id == 0L) {
                            viewModel.addDestino(nuevoDestino)
                        } else {
                            viewModel.editDestino(nuevoDestino)
                        }
                        navController.navigate(Destinations.DestinoMainSC.route)
                    }

                    Spacer()

                    AccionButtonCancel(easyForms = easyForm, "Cancelar") {
                        navController.navigate(Destinations.DestinoMainSC.route)
                    }
                }
            }
        }
    }
}
