package pe.edu.upeu.granturismojpc.ui.presentation.screens.actividad

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
import androidx.compose.material3.Scaffold
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
import pe.edu.upeu.granturismojpc.model.ActividadDto
import pe.edu.upeu.granturismojpc.model.toDto
import pe.edu.upeu.granturismojpc.ui.navigation.Destinations
import pe.edu.upeu.granturismojpc.ui.presentation.components.Spacer
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.AccionButtonCancel
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.AccionButtonSuccess
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.ComboBox
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.MyFormKeys
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.NameTextField
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ActividadForm(
    text: String,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: ActividadFormViewModel= hiltViewModel()
) {
    val actividad by viewModel.actividad.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    //val tipos by viewModel.tipos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDatosPrevios()
    }

    var actividadD: ActividadDto
    if (text!="0"){
        actividadD = Gson().fromJson(text, ActividadDto::class.java)
        LaunchedEffect(Unit) {
            viewModel.getActividad(actividadD.idActividad)
        }
        actividad?.let {
            actividadD=it.toDto()
            Log.i("DMPX","Actividad: ${actividadD.toString()}")
        }
    }else{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val ahora = LocalDateTime.now().format(formatter)
        actividadD= ActividadDto(
            0, "", "", "", 0, "", 0.0
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    formulario(
        actividadD.idActividad!!,
        darkMode,
        navController,
        actividadD,
        viewModel,
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
               actividad: ActividadDto,
               viewModel: ActividadFormViewModel,
){
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val ahora = LocalDateTime.now().format(formatter)
    val actv= ActividadDto(
        0, "", "", "", 0, "", 0.0
    )
    Scaffold(modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom =
        32.dp)){
        BuildEasyForms { easyForm ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                NameTextField(easyForms = easyForm, text=actividad?.titulo!!,"Título de la Actividad:", MyFormKeys.NAME )
                NameTextField(easyForms = easyForm, text=actividad?.descripcion.toString()!!,"Descripción:", MyFormKeys.DESCRIPTION )
                //DateTimePickerCustom(easyForm = easyForm, label = "Fecha de registro:", texts = actividad?.fechaRegistro ?: "", key = MyFormKeys.FECHA, formDP = "yyyy-MM-dd HH:mm:ss")
                NameTextField(easyForms = easyForm, text=actividad?.precioBase.toString()!!,"Precio Base:", MyFormKeys.PU_OLD )
                NameTextField(easyForms = easyForm, text=actividad?.imagenUrl.toString()!!,"URL de Imagen:", MyFormKeys.URL )
                NameTextField(easyForms = easyForm, text=actividad?.tipo.toString()!!,"Tipo:", MyFormKeys.UTILIDAD )
                NameTextField(easyForms = easyForm, text=actividad?.duracionHoras.toString()!!,"Duracion (en horas):", MyFormKeys.HORAREG )


                //NameTextField(easyForms = easyForm, text=actividad?.fechaInicio.toString()!!,"Fecha de inicio:", MyFormKeys.FECHA )
                //NameTextField(easyForms = easyForm, text=actividad?.fechaFin.toString()!!,"Fecha Fin:", MyFormKeys.DATE2 )
                //NameTextField(easyForms = easyForm, text=actividad?.destino.toString()!!,"ID Destino:", MyFormKeys.MODFH )

                Row(Modifier.align(Alignment.CenterHorizontally)){
                    AccionButtonSuccess(easyForms = easyForm, "Guardar", id){
                        val lista=easyForm.formData()
                        actv.titulo=(lista.get(0) as EasyFormsResult.StringResult).value
                        actv.descripcion=(lista.get(1) as EasyFormsResult.StringResult).value
                        //actv.unidadMedida= (splitCadena((lista.get(3) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        actv.precioBase=((lista.get(2) as EasyFormsResult.StringResult).value).toDouble()
                        actv.imagenUrl=((lista.get(3) as EasyFormsResult.StringResult).value)
                        actv.tipo=((lista.get(4) as EasyFormsResult.StringResult).value)
                        actv.duracionHoras=((lista.get(5) as EasyFormsResult.StringResult).value).toLong()

                        if (id==0L.toLong()){
                            Log.i("AGREGAR", "P:"+ actv.tipo)
                            //Log.i("AGREGAR", "VI:"+ actv.stock)
                            viewModel.addActividad(actv)
                        }else{
                            actv.idActividad=id
                            Log.i("MODIFICAR", "M:"+actv)
                            viewModel.editActividad(actv)
                        }
                        navController.navigate(Destinations.ActividadMainSC.route)
                    }
                    Spacer()
                    AccionButtonCancel(easyForms = easyForm, "Cancelar"){
                        navController.navigate(Destinations.ActividadMainSC.route)
                    }
                }
            }
        }
    }

}

fun splitCadena(data:String):String{
    return if(data!="") data.split("-")[0] else ""
}