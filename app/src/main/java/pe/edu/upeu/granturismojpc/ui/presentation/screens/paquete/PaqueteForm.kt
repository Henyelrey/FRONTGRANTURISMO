package pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete

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
import pe.edu.upeu.granturismojpc.model.DestinoResp
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.ProveedorResp
import pe.edu.upeu.granturismojpc.model.toDto
import pe.edu.upeu.granturismojpc.ui.navigation.Destinations
import pe.edu.upeu.granturismojpc.ui.presentation.components.Spacer
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.AccionButtonCancel
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.AccionButtonSuccess
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.ComboBox
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.ComboBoxThre
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.ComboBoxTwo
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.DateTimePickerCustom
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.MyEasyFormsCustomStringResult
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.MyFormKeys
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.NameTextField
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun PaqueteForm(
    text: String,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: PaqueteFormViewModel= hiltViewModel()
) {
    val paquete by viewModel.paquete.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val proveedores by viewModel.provs.collectAsState()
    val destinos by viewModel.dests.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDatosPrevios()
    }

    var paqueteD: PaqueteDto
    if (text!="0"){
        paqueteD = Gson().fromJson(text, PaqueteDto::class.java)
        LaunchedEffect(Unit) {
            viewModel.getPaquete(paqueteD.idPaquete)
        }
        paquete?.let {
            paqueteD=it.toDto()
            Log.i("DMPX","Paquete: ${paqueteD.toString()}")
        }
    }else{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val ahora = LocalDateTime.now().format(formatter)
        paqueteD= PaqueteDto(
            0, "", "", 0.0, "", "", 0, "", "", 0, 0, ahora, ahora, 0
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    formulario(
        paqueteD.idPaquete!!,
        darkMode,
        navController,
        paqueteD,
        viewModel,
        proveedores,
        destinos
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
               paquete: PaqueteDto,
               viewModel: PaqueteFormViewModel,
               listProveedor: List<ProveedorResp>,
               listDestino: List<DestinoResp>,
               ){
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val ahora = LocalDateTime.now().format(formatter)
    val pack= PaqueteDto(
        0, "", "", 0.0, "", "", 0, "", "", 0, 0, ahora, ahora, 0
    )
    Scaffold(modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom =
        32.dp)){
        BuildEasyForms { easyForm ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                NameTextField(easyForms = easyForm, text=paquete?.titulo!!,"Nomb. Paquete:", MyFormKeys.NAME )
                val listM: List<ComboModel> = listProveedor.map { proveedor ->
                    ComboModel(code = proveedor.idProveedor.toString(), name = proveedor.nombreCompleto)
                }
                ComboBox(easyForm = easyForm, "Proveedor:", paquete?.proveedor?.let { it.toString() } ?: "", listM)
                val listD: List<ComboModel> = listDestino.map { destino ->
                    ComboModel(code = destino.idDestino.toString(), name = destino.nombre)
                }
                ComboBoxTwo(easyForm = easyForm, "Destino:", paquete?.proveedor?.let { it.toString() } ?: "", listD)
                /*val listC: List<ComboModel> = listCategoria.map { categor ->
                    ComboModel(code = categor.idCategoria.toString(), name = categor.nombre)
                }
                ComboBoxTwo(easyForm = easyForm, "Categoría:", paquete?.categoria?.let { it.toString() } ?: "", listC)
                val listUM: List<ComboModel> = listUnidMed.map { unitMed ->
                    ComboModel(code = unitMed.idUnidad.toString(), name = unitMed.nombreMedida)
                }*/

                //ComboBoxThre(easyForm = easyForm, label = "Unidad Medida:", paquete?.unidadMedida?.let { it.toString() } ?: "", list =listUM)

                NameTextField(easyForms = easyForm, text=paquete?.descripcion.toString()!!,"Descripción:", MyFormKeys.DESCRIPTION )
                NameTextField(easyForms = easyForm, text=paquete?.precioTotal.toString()!!,"Precio:", MyFormKeys.PU )
                NameTextField(easyForms = easyForm, text=paquete?.imagenUrl.toString()!!,"ImagenURL:", MyFormKeys.URL )
                NameTextField(easyForms = easyForm, text=paquete?.estado.toString()!!,"Estado:", MyFormKeys.STOCK )
                NameTextField(easyForms = easyForm, text=paquete?.duracionDias.toString()!!,"Duración en días:", MyFormKeys.UTILIDAD )

                NameTextField(easyForms = easyForm, text=paquete?.localidad.toString()!!,"Localidad:", MyFormKeys.LOCATION )
                NameTextField(easyForms = easyForm, text=paquete?.tipoActividad.toString()!!,"Tipo de Acividad:", MyFormKeys.ACTIVIDADID )
                NameTextField(easyForms = easyForm, text=paquete?.cuposMaximos.toString()!!,"Cupos Máximos:", MyFormKeys.PU_OLD )
                //NameTextField(easyForms = easyForm, text=paquete?.proveedorId.toString()!!,"Proveedor:", MyFormKeys.UTILIDAD )
                DateTimePickerCustom(easyForm = easyForm, label = "Fecha de inicio:", texts = paquete?.fechaInicio ?: "", key = MyFormKeys.FECHA, formDP = "yyyy-MM-dd HH:mm:ss")
                DateTimePickerCustom(easyForm = easyForm, label = "Fecha de Fin:", texts = paquete?.fechaFin ?: "", key = MyFormKeys.DATE2, formDP = "yyyy-MM-dd HH:mm:ss")
                //NameTextField(easyForms = easyForm, text=paquete?.fechaInicio.toString()!!,"Fecha de inicio:", MyFormKeys.FECHA )
                //NameTextField(easyForms = easyForm, text=paquete?.fechaFin.toString()!!,"Fecha Fin:", MyFormKeys.DATE2 )
                //NameTextField(easyForms = easyForm, text=paquete?.destino.toString()!!,"ID Destino:", MyFormKeys.MODFH )

                Row(Modifier.align(Alignment.CenterHorizontally)){
                    AccionButtonSuccess(easyForms = easyForm, "Guardar", id){
                        val lista=easyForm.formData()
                        pack.titulo=(lista.get(0) as EasyFormsResult.StringResult).value
                        pack.proveedor= (splitCadena((lista.get(1) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        pack.destino= (splitCadena((lista.get(2) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        //pack.unidadMedida= (splitCadena((lista.get(3) as EasyFormsResult.GenericStateResult<String>).value)).toLong()
                        pack.descripcion=((lista.get(3) as EasyFormsResult.StringResult).value)
                        pack.precioTotal=((lista.get(4) as EasyFormsResult.StringResult).value).toDouble()
                        pack.imagenUrl=((lista.get(5) as EasyFormsResult.StringResult).value)
                        pack.estado=((lista.get(6) as EasyFormsResult.StringResult).value)
                        pack.duracionDias=((lista.get(7) as EasyFormsResult.StringResult).value).toInt()
                        pack.localidad=((lista.get(8) as EasyFormsResult.StringResult).value)
                        pack.tipoActividad=((lista.get(9) as EasyFormsResult.StringResult).value)
                        pack.cuposMaximos=((lista.get(10) as EasyFormsResult.StringResult).value).toInt()
                        //pack.proveedorId=((lista.get(7) as EasyFormsResult.StringResult).value).toLong()
                        pack.fechaInicio = (lista.get(11) as MyEasyFormsCustomStringResult).value
                        pack.fechaFin = (lista.get(12) as MyEasyFormsCustomStringResult).value
                        //pack.destino=((lista.get(12) as EasyFormsResult.StringResult).value).toLong()
                        //pack.fechaInicio=((lista.get(8) as EasyFormsResult.StringResult).value)
                        //pack.fechaFin=((lista.get(9) as EasyFormsResult.StringResult).value)

                        if (id==0L.toLong()){
                            Log.i("AGREGAR", "P:"+ pack.proveedor)
                            //Log.i("AGREGAR", "VI:"+ pack.stock)
                            viewModel.addPaquete(pack)
                        }else{
                            pack.idPaquete=id
                            Log.i("MODIFICAR", "M:"+pack)
                            viewModel.editPaquete(pack)
                        }
                        navController.navigate(Destinations.PaqueteMainSC.route)
                    }
                    Spacer()
                    AccionButtonCancel(easyForms = easyForm, "Cancelar"){
                        navController.navigate(Destinations.PaqueteMainSC.route)
                    }
                }
            }
        }
    }

}

fun splitCadena(data:String):String{
    return if(data!="") data.split("-")[0] else ""
}