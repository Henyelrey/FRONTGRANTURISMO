package pe.edu.upeu.granturismojpc.repository

import android.util.Log
import pe.edu.upeu.granturismojpc.data.remote.RestActividadDetalle
import pe.edu.upeu.granturismojpc.model.ActividadDetalleCreateDto
import pe.edu.upeu.granturismojpc.model.ActividadDetalleDto
import pe.edu.upeu.granturismojpc.model.ActividadDetalleResp
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import javax.inject.Inject

interface ActividadDetalleRepository {
    suspend fun deleteActividadDetalle(actividadDetalle: ActividadDetalleDto): Boolean
    suspend fun deleteActividadDetalleId(id: Long): Boolean
    suspend fun reportarActividadDetalles(): List<ActividadDetalleResp>
    suspend fun buscarActividadDetalleId(id: Long): ActividadDetalleResp
    suspend fun buscarActividadDetallesByPaqueteId(paqueteId: Long): List<ActividadDetalleResp>
    suspend fun insertarActividadDetalle(actividadDetalle: ActividadDetalleCreateDto): Boolean
    suspend fun modificarActividadDetalle(actividadDetalle: ActividadDetalleDto): Boolean
}

class ActividadDetalleRepositoryImpl @Inject constructor(
    private val restActividadDetalle: RestActividadDetalle
): ActividadDetalleRepository {

    override suspend fun deleteActividadDetalle(actividadDetalle: ActividadDetalleDto): Boolean {
        val response = restActividadDetalle.deleteActividadDetalle(
            TokenUtils.TOKEN_CONTENT,
            actividadDetalle.idActividadDetalle
        )
        return response.isSuccessful && response.body()?.message == "true"
    }

    override suspend fun deleteActividadDetalleId(id: Long): Boolean {
        val response = restActividadDetalle.deleteActividadDetalle(
            TokenUtils.TOKEN_CONTENT,
            id
        )
        return response.isSuccessful && response.body()?.message == "true"
    }

    override suspend fun reportarActividadDetalles(): List<ActividadDetalleResp> {
        val response = restActividadDetalle.reportarActividadDetalle(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }

    override suspend fun buscarActividadDetalleId(id: Long): ActividadDetalleResp {
        val response = restActividadDetalle.getActividadDetalleId(TokenUtils.TOKEN_CONTENT, id)
        Log.i("REPO", response.toString())
        return response.body() ?: throw Exception("Detalle de actividad no encontrado")
    }

    override suspend fun insertarActividadDetalle(actividadDetalle: ActividadDetalleCreateDto): Boolean {
        Log.i("REPO", actividadDetalle.toString())
        try {
            val response = restActividadDetalle.insertarActividadDetalle(
                TokenUtils.TOKEN_CONTENT,
                actividadDetalle
            )
            Log.i("REPO", "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                return true
            }

            Log.i("REPO", "ErrorBody: ${response.errorBody()?.string()}")
            return false
        } catch (e: Exception) {
            Log.e("REPO", "Error al insertar detalle de actividad", e)
            return false
        }
    }

    override suspend fun modificarActividadDetalle(actividadDetalle: ActividadDetalleDto): Boolean {
        val response = restActividadDetalle.actualizarActividadDetalle(
            TokenUtils.TOKEN_CONTENT,
            actividadDetalle.idActividadDetalle,
            actividadDetalle
        )
        return response.isSuccessful && response.body()?.idActividadDetalle != null
    }
    override suspend fun buscarActividadDetallesByPaqueteId(paqueteId: Long): List<ActividadDetalleResp> {
        try {
            // Primero obtenemos todos los detalles
            val allDetalles = reportarActividadDetalles()
            // Filtramos por ID de actividad
            return allDetalles.filter { it.paquete?.idPaquete == paqueteId }
        } catch (e: Exception) {
            Log.e("REPO", "Error al buscar detalles por ID de paquete", e)
            return emptyList()
        }
    }
}