package pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioalimentacion

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
import pe.edu.upeu.granturismojpc.model.ServicioAlimentacionDto
import pe.edu.upeu.granturismojpc.model.ServicioResp
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
fun ServicioAlimentacionForm(
    text: String,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: ServicioAlimentacionFormViewModel= hiltViewModel()
) {
    val servicioAlimentacion by viewModel.servicioAlimentacion.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val servicios by viewModel.servs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDatosPrevios()
    }

    var servicioAlimentacionD: ServicioAlimentacionDto
    if (text!="0"){
        servicioAlimentacionD = Gson().fromJson(text, ServicioAlimentacionDto::class.java)
        LaunchedEffect(Unit) {
            viewModel.getServicioAlimentacion(servicioAlimentacionD.idAlimentacion)
        }
        servicioAlimentacion?.let {
            servicioAlimentacionD=it.toDto()
            Log.i("DMPX","ServicioAlimentacion: ${servicioAlimentacionD.toString()}")
        }
    }else{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val ahora = LocalDateTime.now().format(formatter)
        servicioAlimentacionD= ServicioAlimentacionDto(
            0, "", "", "", 0
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    formulario(
        servicioAlimentacionD.idAlimentacion!!,
        darkMode,
        navController,
        servicioAlimentacionD,
        viewModel,
        servicios,
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
               servicioAlimentacion: ServicioAlimentacionDto,
               viewModel: ServicioAlimentacionFormViewModel,
               listServicio: List<ServicioResp>,
){
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val ahora = LocalDateTime.now().format(formatter)
    val serv= ServicioAlimentacionDto(
        0, "", "", "", 0
    )
    Scaffold(modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom =
        32.dp)){
        BuildEasyForms { easyForm ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                NameTextField(easyForms = easyForm, text=servicioAlimentacion?.tipoComida!!,"Nomb. ServicioAlimentacion:", MyFormKeys.NAME )
                val listS: List<ComboModel> = listServicio.map { servicio ->
                    ComboModel(code = servicio.idServicio.toString(), name = servicio.nombreServicio)
                }
                ComboBox(easyForm = easyForm, "Servicio:", servicioAlimentacion?.servicio?.let { it.toString() } ?: "", listS)
                /*val listC: List<ComboModel> = listCategoria.map { categor ->
                    ComboModel(code = categor.idCategoria.toString(), name = categor.nombre)
                }
                ComboBoxTwo(easyForm = easyForm, "Categoría:", servicioAlimentacion?.categoria?.let { it.toString() } ?: "", listC)
                val listUM: List<ComboModel> = listUnidMed.map { unitMed ->
                    ComboModel(code = unitMed.idUnidad.toString(), name = unitMed.nombreMedida)
                }*/

                //ComboBoxThre(easyForm = easyForm, label = "Unidad Medida:", servicioAlimentacion?.unidadMedida?.let { it.toString() } ?: "", list =listUM)

                NameTextField(easyForms = easyForm, text=servicioAlimentacion?.estiloGastronomico.toString()!!,"Estilo Gastronómico:", MyFormKeys.DESCRIPTION )
                //DateTimePickerCustom(easyForm = easyForm, label = "Fecha de registro:", texts = servicioAlimentacion?.fechaRegistro ?: "", key = MyFormKeys.FECHA, formDP = "yyyy-MM-dd HH:mm:ss")
                NameTextField(easyForms = easyForm, text=servicioAlimentacion?.incluyeBebidas.toString()!!,"Incluye Bebidas:", MyFormKeys.PU_OLD )
                //NameTextField(easyForms = easyForm, text=servicioAlimentacion?.fechaInicio.toString()!!,"Fecha de inicio:", MyFormKeys.FECHA )
                //NameTextField(easyForms = easyForm, text=servicioAlimentacion?.fechaFin.toString()!!,"Fecha Fin:", MyFormKeys.DATE2 )
                //NameTextField(easyForms = easyForm, text=servicioAlimentacion?.destino.toString()!!,"ID Destino:", MyFormKeys.MODFH )

                Row(Modifier.align(Alignment.CenterHorizontally)){
                    AccionButtonSuccess(easyForms = easyForm, "Guardar", id){
                        val lista=easyForm.formData()
                        serv.tipoComida=(lista.get(0) as EasyFormsResult.StringResult).value
                        serv.servicio= (splitCadena((lista.get(1) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        //serv.unidadMedida= (splitCadena((lista.get(3) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        serv.estiloGastronomico=((lista.get(2) as EasyFormsResult.StringResult).value)
                        serv.incluyeBebidas=((lista.get(3) as EasyFormsResult.StringResult).value)

                        if (id==0L.toLong()){
                            Log.i("AGREGAR", "P:"+ serv.servicio)
                            //Log.i("AGREGAR", "VI:"+ serv.stock)
                            viewModel.addServicioAlimentacion(serv)
                        }else{
                            serv.idAlimentacion=id
                            Log.i("MODIFICAR", "M:"+serv)
                            viewModel.editServicioAlimentacion(serv)
                        }
                        navController.navigate(Destinations.ServicioAlimentacionMainSC.route)
                    }
                    Spacer()
                    AccionButtonCancel(easyForms = easyForm, "Cancelar"){
                        navController.navigate(Destinations.ServicioAlimentacionMainSC.route)
                    }
                }
            }
        }
    }

}

fun splitCadena(data:String):String{
    return if(data!="") data.split("-")[0] else ""
}