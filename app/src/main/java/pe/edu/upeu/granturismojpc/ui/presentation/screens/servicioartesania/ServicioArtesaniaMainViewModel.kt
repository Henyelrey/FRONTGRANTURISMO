package pe.edu.upeu.granturismojpc.ui.presentation.screens.servicioartesania

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.ServicioArtesaniaDto
import pe.edu.upeu.granturismojpc.model.ServicioArtesaniaResp
import pe.edu.upeu.granturismojpc.repository.ServicioArtesaniaRepository
import javax.inject.Inject

@HiltViewModel
class ServicioArtesaniaMainViewModel  @Inject constructor(
    private val servartRepo: ServicioArtesaniaRepository,
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _servarts = MutableStateFlow<List<ServicioArtesaniaResp>>(emptyList())
    val servarts: StateFlow<List<ServicioArtesaniaResp>> = _servarts

    init {
        cargarServicioArtesaniaes()
    }

    fun cargarServicioArtesaniaes() {
        viewModelScope.launch {
            _isLoading.value = true
            _servarts.value = servartRepo.reportarServicioArtesaniaes()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<ServicioArtesaniaResp> = flow {
        emit(servartRepo.buscarServicioArtesaniaId(id))
    }

    fun eliminar(servicioArtesania: ServicioArtesaniaDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = servartRepo.deleteServicioArtesania(servicioArtesania)
            if (success) {
                //eliminarServicioArtesaniaDeLista(servicioArtesania.idServicioArtesania)
                cargarServicioArtesaniaes()
                _deleteSuccess.value = success
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("ServicioArtesaniaVM", "Error al eliminar servicioArtesania", e)
            _deleteSuccess.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }
    fun eliminarServicioArtesaniaDeLista(id: Long) {
        _servarts.value = _servarts.value.filterNot { it.idArtesania == id }
    }
}