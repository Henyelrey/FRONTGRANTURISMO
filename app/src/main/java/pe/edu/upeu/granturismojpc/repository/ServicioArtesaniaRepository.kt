package pe.edu.upeu.granturismojpc.repository

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pe.edu.upeu.granturismojpc.data.remote.RestServicioArtesania
import pe.edu.upeu.granturismojpc.model.ServicioArtesaniaCreateDto
import pe.edu.upeu.granturismojpc.model.ServicioArtesaniaDto
import pe.edu.upeu.granturismojpc.model.ServicioArtesaniaResp
import pe.edu.upeu.granturismojpc.model.ServicioResp
import pe.edu.upeu.granturismojpc.model.TipoServicioResp
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import javax.inject.Inject
import javax.inject.Singleton

interface ServicioArtesaniaRepository {
    suspend fun deleteServicioArtesania(servicioArtesania: ServicioArtesaniaDto): Boolean
    suspend fun reportarServicioArtesaniaes(): List<ServicioArtesaniaResp>
    suspend fun buscarServicioArtesaniaId(id: Long): ServicioArtesaniaResp
    suspend fun insertarServicioArtesania(servicioArtesania: ServicioArtesaniaCreateDto): Boolean
    suspend fun modificarServicioArtesania(servicioArtesania: ServicioArtesaniaDto): Boolean
}

class ServicioArtesaniaRepositoryImp @Inject constructor(
    private val restServicioArtesania: RestServicioArtesania,
): ServicioArtesaniaRepository {

    override suspend fun deleteServicioArtesania(servicioArtesania: ServicioArtesaniaDto): Boolean {
        val response =
            restServicioArtesania.deleteServicioArtesania(TokenUtils.TOKEN_CONTENT, servicioArtesania.idArtesania)
        return response.isSuccessful && response.body()?.message == "true"
    }

    override suspend fun reportarServicioArtesaniaes(): List<ServicioArtesaniaResp> {
        val response =
            restServicioArtesania.reportarServicioArtesania(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }

    override suspend fun buscarServicioArtesaniaId(id: Long): ServicioArtesaniaResp {
        val response =
            restServicioArtesania.getServicioArtesaniaId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("ServicioArtesania no encontrado")
    }

    override suspend fun insertarServicioArtesania(servicioArtesania: ServicioArtesaniaCreateDto): Boolean {
        Log.i("REPO", servicioArtesania.toString())
        try {
            val response = restServicioArtesania.insertarServicioArtesania(TokenUtils.TOKEN_CONTENT, servicioArtesania)
            Log.i("REPO", "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                return true
            }

            Log.i("REPO", "ErrorBody: ${response.errorBody()?.string()}")
            return false
        } catch (e: Exception) {
            Log.e("REPO", "Error al insertar servicioArtesania", e)
            return false
        }
    }

    override suspend fun modificarServicioArtesania(servicioArtesania: ServicioArtesaniaDto): Boolean {
        val response =
            restServicioArtesania.actualizarServicioArtesania(TokenUtils.TOKEN_CONTENT, servicioArtesania.idArtesania, servicioArtesania)
        return response.isSuccessful && response.body()?.idArtesania != null
    }
}
