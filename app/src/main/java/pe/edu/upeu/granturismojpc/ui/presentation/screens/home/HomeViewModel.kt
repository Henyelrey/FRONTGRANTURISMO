package pe.edu.upeu.granturismojpc.ui.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prodRepo: PaqueteRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _prods = MutableStateFlow<List<PaqueteResp>>(emptyList())
    val prods: StateFlow<List<PaqueteResp>> = _prods


    fun cargarPaquetes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _prods.value = prodRepo.reportarPaquetes() // 👈 Asegúrate de que esta función existe en tu repositorio
            } catch (e: Exception) {
                // Manejo de errores
            } finally {
                _isLoading.value = false
            }
        }
    }

}