package pe.edu.upeu.granturismojpc.ui.presentation.screens.servicio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ServicioDto
import pe.edu.upeu.granturismojpc.model.ServicioResp
import pe.edu.upeu.granturismojpc.repository.ServicioRepository
import javax.inject.Inject

@HiltViewModel
class ServicioMainViewModel  @Inject constructor(
    private val servRepo: ServicioRepository,
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _provs = MutableStateFlow<List<ServicioResp>>(emptyList())
    val provs: StateFlow<List<ServicioResp>> = _provs

    init {
        cargarServicios()
    }

    fun cargarServicios() {
        viewModelScope.launch {
            _isLoading.value = true
            _provs.value = servRepo.reportarServicios()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<ServicioResp> = flow {
        emit(servRepo.buscarServicioId(id))
    }

    fun eliminar(servicio: ServicioDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = servRepo.deleteServicio(servicio)
            if (success) {
                //eliminarServicioDeLista(servicio.idServicio)
                cargarServicios()
                _deleteSuccess.value = success
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("ServicioVM", "Error al eliminar servicio", e)
            _deleteSuccess.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }
    fun eliminarServicioDeLista(id: Long) {
        _provs.value = _provs.value.filterNot { it.idServicio == id }
    }
}