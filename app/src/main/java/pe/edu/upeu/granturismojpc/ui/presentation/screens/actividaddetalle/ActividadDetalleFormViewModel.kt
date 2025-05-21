package pe.edu.upeu.granturismojpc.ui.presentation.screens.actividaddetalle

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ActividadDetalleCreateDto
import pe.edu.upeu.granturismojpc.model.ActividadDetalleDto
import pe.edu.upeu.granturismojpc.model.ActividadDetalleResp
import pe.edu.upeu.granturismojpc.model.ActividadResp
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.repository.ActividadDetalleRepository
import pe.edu.upeu.granturismojpc.repository.ActividadRepository
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import javax.inject.Inject

@HiltViewModel
class ActividadDetalleFormViewModel @Inject constructor(
    private val detalleRepo: ActividadDetalleRepository,
    private val actvRepo: ActividadRepository,
    private val packRepo: PaqueteRepository,
    /*private val cateRepo: CategoriaRepository,
    private val umRepo: UnidadMedidaRepository,*/
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _actividadDetalle = MutableStateFlow<ActividadDetalleResp?>(null)
    val actividadDetalle: StateFlow<ActividadDetalleResp?> = _actividadDetalle

    private val _actvs = MutableStateFlow<List<ActividadResp>>(emptyList())
    val actvs: StateFlow<List<ActividadResp>> = _actvs

    private val _packs = MutableStateFlow<List<PaqueteResp>>(emptyList())
    val packs: StateFlow<List<PaqueteResp>> = _packs

    /*private val _categors = MutableStateFlow<List<Categoria>>(emptyList())
    val categors: StateFlow<List<Categoria>> = _categors

    private val _unidMeds = MutableStateFlow<List<UnidadMedida>>(emptyList())
    val unidMeds: StateFlow<List<UnidadMedida>> = _unidMeds*/

    fun getActividadDetalle(idX: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _actividadDetalle.value = detalleRepo.buscarActividadDetalleId(idX)
            _isLoading.value = false
        }
    }

    fun getDatosPrevios() {
        viewModelScope.launch {
            _actvs.value = actvRepo.reportarActividades()
            Log.i("REAL", "Actvs: ${_actvs.value}")
            _packs.value = packRepo.reportarPaquetes()
            Log.i("REAL", "Packs: ${_packs.value}")
            //_categors.value = cateRepo.findAll()
            //_unidMeds.value = umRepo.findAll()
        }
    }

    fun addActividadDetalle(actividadDetalle: ActividadDetalleDto) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            // Convertir ActividadDetalleDto a ActividadDetalleCreateDto para excluir el idActividadDetalle
            val actividadDetalleCreateDto = ActividadDetalleCreateDto(
                titulo = actividadDetalle.titulo,
                descripcion = actividadDetalle.descripcion,
                imagenUrl = actividadDetalle.imagenUrl,
                orden = actividadDetalle.orden,
                paquete = actividadDetalle.paquete,
                actividad = actividadDetalle.actividad,
                )

            Log.i("REAL", "Creando actividadDetalle: $actividadDetalleCreateDto")
            detalleRepo.insertarActividadDetalle(actividadDetalleCreateDto)
            _isLoading.value = false
        }
    }

    fun editActividadDetalle(actividadDetalle: ActividadDetalleDto){
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.value = true
            detalleRepo.modificarActividadDetalle(actividadDetalle)
            _isLoading.value = false
        }
    }


}