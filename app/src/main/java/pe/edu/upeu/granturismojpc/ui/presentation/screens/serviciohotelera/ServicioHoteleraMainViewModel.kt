package pe.edu.upeu.granturismojpc.ui.presentation.screens.serviciohotelera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ServicioHoteleraDto
import pe.edu.upeu.granturismojpc.model.ServicioHoteleraResp
import pe.edu.upeu.granturismojpc.repository.ServicioHoteleraRepository
import javax.inject.Inject

@HiltViewModel
class ServicioHoteleraMainViewModel  @Inject constructor(
    private val servhotRepo: ServicioHoteleraRepository,
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _servhots = MutableStateFlow<List<ServicioHoteleraResp>>(emptyList())
    val servhots: StateFlow<List<ServicioHoteleraResp>> = _servhots

    init {
        cargarServicioHoteleraes()
    }

    fun cargarServicioHoteleraes() {
        viewModelScope.launch {
            _isLoading.value = true
            _servhots.value = servhotRepo.reportarServicioHotelera()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<ServicioHoteleraResp> = flow {
        emit(servhotRepo.buscarServicioHoteleraId(id))
    }

    fun eliminar(servicioHotelera: ServicioHoteleraDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = servhotRepo.deleteServicioHotelera(servicioHotelera)
            if (success) {
                //eliminarServicioHoteleraDeLista(servicioHotelera.idServicioHotelera)
                cargarServicioHoteleraes()
                _deleteSuccess.value = success
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("ServicioHoteleraVM", "Error al eliminar servicioHotelera", e)
            _deleteSuccess.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }
    fun eliminarServicioHoteleraDeLista(id: Long) {
        _servhots.value = _servhots.value.filterNot { it.idHoteleria == id }
    }
}