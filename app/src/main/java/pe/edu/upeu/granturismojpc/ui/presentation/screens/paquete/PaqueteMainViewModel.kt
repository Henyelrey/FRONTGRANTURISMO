package pe.edu.upeu.granturismojpc.ui.presentation.screens.paquete

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import javax.inject.Inject

@HiltViewModel
class PaqueteMainViewModel  @Inject constructor(
    private val prodRepo: PaqueteRepository,
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> get() = _deleteSuccess

    private val _prods = MutableStateFlow<List<PaqueteResp>>(emptyList())
    val prods: StateFlow<List<PaqueteResp>> = _prods

    init {
        cargarPaquetes()
    }

    fun cargarPaquetes() {
        viewModelScope.launch {
            _isLoading.value = true
            _prods.value = prodRepo.reportarPaquetes()
            _isLoading.value = false
        }
    }

    fun buscarPorId(id: Long): Flow<PaqueteResp> = flow {
        emit(prodRepo.buscarPaqueteId(id))
    }

    fun eliminar(paquete: PaqueteDto) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val success = prodRepo.deletePaquete(paquete)
            if (success) {
                //eliminarPaqueteDeLista(paquete.idPaquete)
                cargarPaquetes()
                _deleteSuccess.value = success
            }else{ _deleteSuccess.value = false }
        } catch (e: Exception) {
            Log.e("PaqueteVM", "Error al eliminar paquete", e)
            _deleteSuccess.value = false
        }
    }

    fun clearDeleteResult() {
        _deleteSuccess.value = null
    }
    fun eliminarPaqueteDeLista(id: Long) {
        _prods.value = _prods.value.filterNot { it.idPaquete == id }
    }
}