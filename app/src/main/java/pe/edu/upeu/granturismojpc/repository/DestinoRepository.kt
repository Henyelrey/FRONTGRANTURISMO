package pe.edu.upeu.granturismojpc.repository

import android.util.Log
import jakarta.inject.Inject
import pe.edu.upeu.granturismojpc.data.remote.RestDestino
import pe.edu.upeu.granturismojpc.model.DestinoCreateDto
import pe.edu.upeu.granturismojpc.model.DestinoDto
import pe.edu.upeu.granturismojpc.model.DestinoResp
import pe.edu.upeu.granturismojpc.utils.TokenUtils

interface DestinoRepository {
    suspend fun findAll(): List<DestinoResp>
    suspend fun deleteDestino(destino: DestinoDto): Boolean
    suspend fun reportarDestinos(): List<DestinoResp>
    suspend fun buscarDestinoId(id: Long): DestinoResp
    suspend fun insertarDestino(destino: DestinoCreateDto): Boolean
    suspend fun modificarDestino(destino: DestinoDto): Boolean
}
class DestinoRepositoryImp @Inject constructor(
    private val restDestino: RestDestino,
): DestinoRepository{
    override suspend fun findAll(): List<DestinoResp> {
        val response =
            restDestino.reportarDestinos(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }

    override suspend fun deleteDestino(destino: DestinoDto): Boolean {
        val response =
            restDestino.deleteDestino(TokenUtils.TOKEN_CONTENT, destino.idDestino)
        return response.isSuccessful && response.body()?.message == "true"
    }

    override suspend fun reportarDestinos(): List<DestinoResp> {
        val response =
            restDestino.reportarDestinos(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }

    override suspend fun buscarDestinoId(id: Long): DestinoResp {
        val response =
            restDestino.getDestinoId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("ServicioHotelera no encontrado")
    }

    override suspend fun insertarDestino(destino: DestinoCreateDto): Boolean {
        Log.i("REPO", destino.toString())
        try {
            val response = restDestino.insertarDestino(TokenUtils.TOKEN_CONTENT, destino)
            Log.i("REPO", "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                return true
            }

            Log.i("REPO", "ErrorBody: ${response.errorBody()?.string()}")
            return false
        } catch (e: Exception) {
            Log.e("REPO", "Error al insertar destino", e)
            return false
        }
    }

    override suspend fun modificarDestino(destino: DestinoDto): Boolean {
        val response =
            restDestino.actualizarDestino(TokenUtils.TOKEN_CONTENT, destino.idDestino, destino)
        return response.isSuccessful && response.body()?.idDestino != null
    }
}