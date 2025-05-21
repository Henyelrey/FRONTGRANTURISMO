package pe.edu.upeu.granturismojpc.ui.presentation.screens.actividaddetalle

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.google.gson.Gson
import pe.edu.upeu.granturismojpc.model.ComboModel
import pe.edu.upeu.granturismojpc.model.ActividadDetalleDto
import pe.edu.upeu.granturismojpc.model.ActividadResp
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.model.toDto
import pe.edu.upeu.granturismojpc.ui.navigation.Destinations
import pe.edu.upeu.granturismojpc.ui.presentation.components.Spacer
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.AccionButtonCancel
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.AccionButtonSuccess
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.ComboBox
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.ComboBoxTwo
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.MyFormKeys
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.NameTextField
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ActividadDetalleForm(
    text: String,
    packId: String,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: ActividadDetalleFormViewModel= hiltViewModel()
) {
    val actividadDetalle by viewModel.actividadDetalle.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val packs by viewModel.packs.collectAsState()
    val actvs by viewModel.actvs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDatosPrevios()
    }

    var actividadDetalleD: ActividadDetalleDto
    if (text!="0"){
        actividadDetalleD = Gson().fromJson(text, ActividadDetalleDto::class.java)
        LaunchedEffect(Unit) {
            viewModel.getActividadDetalle(actividadDetalleD.idActividadDetalle)
        }
        actividadDetalle?.let {
            actividadDetalleD=it.toDto()
            Log.i("DMPX","ActividadDetalle: ${actividadDetalleD.toString()}")
        }
    }else{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val ahora = LocalDateTime.now().format(formatter)
        actividadDetalleD= ActividadDetalleDto(
            0, "", "", "", 0, packId.toLongOrNull() ?: 0L, 0
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    formulario(
        actividadDetalleD.idActividadDetalle!!,
        darkMode,
        navController,
        actividadDetalleD,
        viewModel,
        actvs,
        packs,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission",
    "CoroutineCreationDuringComposition"
)
@Composable
fun formulario(id:Long,
               darkMode: MutableState<Boolean>,
               navController: NavHostController,
               actividadDetalle: ActividadDetalleDto,
               viewModel: ActividadDetalleFormViewModel,
               listActividad: List<ActividadResp>,
               listPaquete: List<PaqueteResp>,
){
    val paqueteId = actividadDetalle.paquete.toString()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val ahora = LocalDateTime.now().format(formatter)
    val serv= ActividadDetalleDto(
        0, "", "", "", 0, actividadDetalle.paquete, 0
    )
    Scaffold(modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom =
        32.dp)){
        BuildEasyForms { easyForm ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                val listA: List<ComboModel> = listActividad.map { actividad ->
                    ComboModel(code = actividad.idActividad.toString(), name = actividad.titulo)
                }
                ComboBox(easyForm = easyForm, "Actividad:", actividadDetalle?.actividad?.let { it.toString() } ?: "", listA)
                /*val listP: List<ComboModel> = listPaquete.map { paquete ->
                    ComboModel(code = paquete.idPaquete.toString(), name = paquete.titulo)
                }
                ComboBoxTwo(easyForm = easyForm, "Paquete:", actividadDetalle?.paquete?.let { it.toString() } ?: "", listP)
                */
                Text(
                    text = "Paquete: ${paqueteId}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                NameTextField(easyForms = easyForm, text=actividadDetalle?.titulo.toString()!!,"Título:", MyFormKeys.NAME )
                NameTextField(easyForms = easyForm, text=actividadDetalle?.descripcion.toString()!!,"Descripción:", MyFormKeys.PU_OLD )
                NameTextField(easyForms = easyForm, text=actividadDetalle?.imagenUrl.toString()!!,"URL de Imagen:", MyFormKeys.URL )
                NameTextField(easyForms = easyForm, text=actividadDetalle?.orden.toString()!!,"Orden:", MyFormKeys.UTILIDAD )


                Row(Modifier.align(Alignment.CenterHorizontally)){
                    AccionButtonSuccess(easyForms = easyForm, "Guardar", id){
                        val lista=easyForm.formData()
                        serv.actividad= (splitCadena((lista.get(0) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        serv.paquete= paqueteId.toLong()
                        //serv.unidadMedida= (splitCadena((lista.get(3) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        serv.titulo=((lista.get(1) as EasyFormsResult.StringResult).value)
                        serv.descripcion=((lista.get(2) as EasyFormsResult.StringResult).value)
                        serv.imagenUrl=((lista.get(3) as EasyFormsResult.StringResult).value)
                        serv.orden=((lista.get(4) as EasyFormsResult.StringResult).value).toInt()

                        if (id==0L.toLong()){
                            Log.i("AGREGAR", "A:"+ serv.actividad)
                            Log.i("AGREGAR", "P:"+ serv.paquete)
                            //Log.i("AGREGAR", "VI:"+ serv.stock)
                            viewModel.addActividadDetalle(serv)
                        }else{
                            serv.idActividadDetalle=id
                            Log.i("MODIFICAR", "M:"+serv)
                            viewModel.editActividadDetalle(serv)
                        }
                        navController.navigate(Destinations.ActividadDetalleMainSC.passId(paqueteId))
                    }
                    Spacer()
                    AccionButtonCancel(easyForms = easyForm, "Cancelar"){
                        navController.navigate(Destinations.ActividadDetalleMainSC.passId(paqueteId))
                    }
                }
            }
        }
    }

}

fun splitCadena(data:String):String{
    return if(data!="") data.split("-")[0] else ""
}