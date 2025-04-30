package pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioalimentacion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ServicioAlimentacionDto
import pe.edu.upeu.granturismojpc.model.ServicioAlimentacionResp
import pe.edu.upeu.granturismojpc.repository.ServicioAlimentacionRepository
import javax.inject.Inject

@HiltViewModel
class ServicioAlimentacionMainViewModel  @Inject constructor(
    private val servaliRepo: ServicioAlimentacionRepository,
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _servalis = MutableStateFlow<List<ServicioAlimentacionResp>>(emptyList())
    val servalis: StateFlow<List<ServicioAlimentacionResp>> = _servalis

    init {
        cargarServicioAlimentaciones()
    }

    fun cargarServicioAlimentaciones() {
        viewModelScope.launch {
            _isLoading.value = true
            _servalis.value = servaliRepo.reportarServicioAlimentaciones()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<ServicioAlimentacionResp> = flow {
        emit(servaliRepo.buscarServicioAlimentacionId(id))
    }

    fun eliminar(servicioAlimentacion: ServicioAlimentacionDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = servaliRepo.deleteServicioAlimentacion(servicioAlimentacion)
            if (success) {
                //eliminarServicioAlimentacionDeLista(servicioAlimentacion.idServicioAlimentacion)
                cargarServicioAlimentaciones()
                _deleteSuccess.value = success
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("ServicioAlimentacionVM", "Error al eliminar servicioAlimentacion", e)
            _deleteSuccess.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }
    fun eliminarServicioAlimentacionDeLista(id: Long) {
        _servalis.value = _servalis.value.filterNot { it.idAlimentacion == id }
    }
}