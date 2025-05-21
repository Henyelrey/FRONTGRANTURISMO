package pe.edu.upeu.granturismojpc.ui.presentation.screens.actividad

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ActividadCreateDto
import pe.edu.upeu.granturismojpc.model.ActividadDto
import pe.edu.upeu.granturismojpc.model.ActividadResp
import pe.edu.upeu.granturismojpc.repository.ActividadRepository
import javax.inject.Inject

@HiltViewModel
class ActividadFormViewModel @Inject constructor(
    private val actvRepo: ActividadRepository,
    //private val tipoRepo: TipoActividadRepository,
    /*private val cateRepo: CategoriaRepository,
    private val umRepo: UnidadMedidaRepository,*/
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _actividad = MutableStateFlow<ActividadResp?>(null)
    val actividad: StateFlow<ActividadResp?> = _actividad

    //private val _tipos = MutableStateFlow<List<TipoActividadResp>>(emptyList())
    //val tipos: StateFlow<List<TipoActividadResp>> = _tipos

    /*private val _categors = MutableStateFlow<List<Categoria>>(emptyList())
    val categors: StateFlow<List<Categoria>> = _categors

    private val _unidMeds = MutableStateFlow<List<UnidadMedida>>(emptyList())
    val unidMeds: StateFlow<List<UnidadMedida>> = _unidMeds*/

    fun getActividad(idX: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _actividad.value = actvRepo.buscarActividadId(idX)
            _isLoading.value = false
        }
    }

    fun getDatosPrevios() {
        viewModelScope.launch {
            //_tipos.value = tipoRepo.findAll()
            //Log.i("REAL", "Tipos: ${_tipos.value}")
            //_categors.value = cateRepo.findAll()
            //_unidMeds.value = umRepo.findAll()
        }
    }

    fun addActividad(actividad: ActividadDto) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            // Convertir ActividadDto a ActividadCreateDto para excluir el idActividad
            val actividadCreateDto = ActividadCreateDto(
                titulo = actividad.titulo,
                descripcion = actividad.descripcion,
                imagenUrl = actividad.imagenUrl,
                tipo = actividad.tipo,
                duracionHoras = actividad.duracionHoras,
                precioBase = actividad.precioBase,
                )

            Log.i("REAL", "Creando actividad: $actividadCreateDto")
            actvRepo.insertarActividad(actividadCreateDto)
            _isLoading.value = false
        }
    }

    fun editActividad(actividad: ActividadDto){
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.value = true
            actvRepo.modificarActividad(actividad)
            _isLoading.value = false
        }
    }


}