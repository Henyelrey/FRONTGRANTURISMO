package pe.edu.upeu.granturismojpc.repository

import android.util.Log
import pe.edu.upeu.granturismojpc.data.remote.RestActividad
import pe.edu.upeu.granturismojpc.model.ActividadCreateDto
import pe.edu.upeu.granturismojpc.model.ActividadDto
import pe.edu.upeu.granturismojpc.model.ActividadResp
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import javax.inject.Inject

interface ActividadRepository {
    suspend fun deleteActividad(actividad: ActividadDto): Boolean
    suspend fun reportarActividades(): List<ActividadResp>
    suspend fun buscarActividadId(id: Long): ActividadResp
    suspend fun insertarActividad(actividad: ActividadCreateDto): Boolean
    suspend fun modificarActividad(actividad: ActividadDto): Boolean
}

class ActividadRepositoryImp @Inject constructor(
    private val restActividad: RestActividad,
): ActividadRepository {

    override suspend fun deleteActividad(actividad: ActividadDto): Boolean {
        val response =
            restActividad.deleteActividad(TokenUtils.TOKEN_CONTENT, actividad.idActividad)
        return response.isSuccessful && response.body()?.message == "true"
    }

    override suspend fun reportarActividades(): List<ActividadResp> {
        val response =
            restActividad.reportarActividades(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }

    override suspend fun buscarActividadId(id: Long): ActividadResp {
        val response =
            restActividad.getActividadId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Actividad no encontrado")
    }

    override suspend fun insertarActividad(actividad: ActividadCreateDto): Boolean {
        Log.i("REPO", actividad.toString())
        try {
            val response = restActividad.insertarActividad(TokenUtils.TOKEN_CONTENT, actividad)
            Log.i("REPO", "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                return true
            }

            Log.i("REPO", "ErrorBody: ${response.errorBody()?.string()}")
            return false
        } catch (e: Exception) {
            Log.e("REPO", "Error al insertar actividad", e)
            return false
        }
    }

    override suspend fun modificarActividad(actividad: ActividadDto): Boolean {
        val response =
            restActividad.actualizarActividad(TokenUtils.TOKEN_CONTENT, actividad.idActividad, actividad)
        return response.isSuccessful && response.body()?.idActividad != null
    }
}