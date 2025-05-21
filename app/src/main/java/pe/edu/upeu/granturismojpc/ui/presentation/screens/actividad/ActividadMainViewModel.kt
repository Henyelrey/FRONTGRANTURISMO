package pe.edu.upeu.granturismojpc.ui.presentation.screens.actividad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ActividadDto
import pe.edu.upeu.granturismojpc.model.ActividadResp
import pe.edu.upeu.granturismojpc.repository.ActividadRepository
import javax.inject.Inject

@HiltViewModel
class ActividadMainViewModel  @Inject constructor(
    private val servRepo: ActividadRepository,
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _actvs = MutableStateFlow<List<ActividadResp>>(emptyList())
    val actvs: StateFlow<List<ActividadResp>> = _actvs

    init {
        cargarActividads()
    }

    fun cargarActividads() {
        viewModelScope.launch {
            _isLoading.value = true
            _actvs.value = servRepo.reportarActividades()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<ActividadResp> = flow {
        emit(servRepo.buscarActividadId(id))
    }

    fun eliminar(actividad: ActividadDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = servRepo.deleteActividad(actividad)
            if (success) {
                //eliminarActividadDeLista(actividad.idActividad)
                cargarActividads()
                _deleteSuccess.value = success
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("ActividadVM", "Error al eliminar actividad", e)
            _deleteSuccess.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }
    fun eliminarActividadDeLista(id: Long) {
        _actvs.value = _actvs.value.filterNot { it.idActividad == id }
    }
}