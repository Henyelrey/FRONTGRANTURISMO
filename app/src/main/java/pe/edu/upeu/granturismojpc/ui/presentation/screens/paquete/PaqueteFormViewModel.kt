package pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.DestinoResp
import pe.edu.upeu.granturismojpc.model.PaqueteCreateDto
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.model.ProveedorResp
import pe.edu.upeu.granturismojpc.repository.DestinoRepository
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import pe.edu.upeu.granturismojpc.repository.ProveedorRepository
import javax.inject.Inject

@HiltViewModel
class PaqueteFormViewModel @Inject constructor(
    private val packRepo: PaqueteRepository,
    private val provRepo: ProveedorRepository,
    private val destRepo: DestinoRepository,
    /*private val cateRepo: CategoriaRepository,
    private val umRepo: UnidadMedidaRepository,*/
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _paquete = MutableStateFlow<PaqueteResp?>(null)
    val paquete: StateFlow<PaqueteResp?> = _paquete

    private val _provs = MutableStateFlow<List<ProveedorResp>>(emptyList())
    val provs: StateFlow<List<ProveedorResp>> = _provs

    private val _dests = MutableStateFlow<List<DestinoResp>>(emptyList())
    val dests: StateFlow<List<DestinoResp>> = _dests

    /*private val _categors = MutableStateFlow<List<Categoria>>(emptyList())
    val categors: StateFlow<List<Categoria>> = _categors

    private val _unidMeds = MutableStateFlow<List<UnidadMedida>>(emptyList())
    val unidMeds: StateFlow<List<UnidadMedida>> = _unidMeds*/

    fun getPaquete(idX: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _paquete.value = packRepo.buscarPaqueteId(idX)
            _isLoading.value = false
        }
    }

    fun getDatosPrevios() {
        viewModelScope.launch {
            _provs.value = provRepo.reportarProveedores()
            _dests.value = destRepo.findAll()
            //_unidMeds.value = umRepo.findAll()
        }
    }

    fun addPaquete(paquete: PaqueteDto) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            // Convertir PaqueteDto a PaqueteCreateDto para excluir el idPaquete
            val paqueteCreateDto = PaqueteCreateDto(
                titulo = paquete.titulo,
                descripcion = paquete.descripcion,
                imagenUrl = paquete.imagenUrl,
                precioTotal = paquete.precioTotal,
                estado = paquete.estado,
                duracionDias = paquete.duracionDias,
                localidad = paquete.localidad,
                tipoActividad = paquete.tipoActividad,
                cuposMaximos = paquete.cuposMaximos,
                proveedor = paquete.proveedor,
                fechaInicio = paquete.fechaInicio,
                fechaFin = paquete.fechaFin,
                destino = paquete.destino
            )

            Log.i("REAL", "Creando paquete: $paqueteCreateDto")
            packRepo.insertarPaquete(paqueteCreateDto)
            _isLoading.value = false
        }
    }

    fun editPaquete(paquete: PaqueteDto){
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.value = true
            packRepo.modificarPaquete(paquete)
            _isLoading.value = false
        }
    }
}