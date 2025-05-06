package pe.edu.upeu.granturismojpc.ui.presentation.screens.destino

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.DestinoCreateDto
import pe.edu.upeu.granturismojpc.model.DestinoDto
import pe.edu.upeu.granturismojpc.model.DestinoResp
import pe.edu.upeu.granturismojpc.model.ServicioHoteleraCreateDto
import pe.edu.upeu.granturismojpc.model.ServicioHoteleraDto
import pe.edu.upeu.granturismojpc.model.ServicioHoteleraResp
import pe.edu.upeu.granturismojpc.model.ServicioResp
import pe.edu.upeu.granturismojpc.repository.DestinoRepository
import pe.edu.upeu.granturismojpc.repository.ServicioHoteleraRepository
import pe.edu.upeu.granturismojpc.repository.ServicioRepository
import javax.inject.Inject

@HiltViewModel
class DestinoFormViewModel @Inject constructor(
    private val destRepo: DestinoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel()  {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _destino = MutableStateFlow<DestinoResp?>(null)
    val destino: StateFlow<DestinoResp?> = _destino
    fun getDestino(idX: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _destino.value = destRepo.buscarDestinoId(idX)
            _isLoading.value = false
        }
    }

    /*fun getDatosPrevios() {
        viewModelScope.launch {
            _servs.value = servRepo.reportarServicios()
            //_categors.value = cateRepo.findAll()
            //_unidMeds.value = umRepo.findAll()
        }
    }
*/

    fun addDestino(destino: DestinoDto) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            // Convertir ServicioHoteleraDto a ServicioHoteleraCreateDto para excluir el idServicioHotelera
            val destinoCreateDto = DestinoCreateDto(
                nombre = destino.nombre,
                descripcion = destino.descripcion,
                ubicacion = destino.ubicacion,
                imagenUrl = destino.imagenUrl,
                latitud = destino.latitud,
                longitud = destino.longitud,
                popularidad = destino.popularidad,
                precioMedio = destino.precioMedio,
                rating = destino.rating,
            )

            Log.i("REAL", "Creando destino: $destinoCreateDto")
            destRepo.insertarDestino(destinoCreateDto)
            _isLoading.value = false
        }
    }

    fun editDestino(destino: DestinoDto){
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.value = true
            destRepo.modificarDestino(destino)
            _isLoading.value = false
        }
    }

}