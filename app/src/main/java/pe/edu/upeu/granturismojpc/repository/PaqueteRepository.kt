package pe.edu.upeu.granturismojpc.repository

import android.util.Log
import pe.edu.upeu.granturismojpc.data.remote.RestPaquete
import pe.edu.upeu.granturismojpc.model.PaqueteCreateDto
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.model.toCreateDto
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import javax.inject.Inject

interface PaqueteRepository{
    suspend fun deletePaquete(paquete: PaqueteDto): Boolean
    suspend fun reportarPaquetes(): List<PaqueteResp> // Cambiado
    suspend fun buscarPaqueteId(id: Long): PaqueteResp // Cambiado
    suspend fun insertarPaquete(paquete: PaqueteCreateDto): Boolean
    suspend fun modificarPaquete(paquete: PaqueteDto): Boolean
}
class PaqueteRepositoryImp @Inject constructor(
    private val restPaquete: RestPaquete,
    //private val actividadDao: ActividadDao,
): PaqueteRepository{
    override suspend fun deletePaquete(paquete: PaqueteDto): Boolean {
        val response =
            restPaquete.deletePaquete(TokenUtils.TOKEN_CONTENT,
                paquete.idPaquete)
        return response.isSuccessful && response.body()?.message ==
                "true"
    }
    override suspend fun reportarPaquetes(): List<PaqueteResp> {
        val response =
            restPaquete.reportarPaquete(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }
    override suspend fun buscarPaqueteId(id: Long): PaqueteResp {
        val response =
            restPaquete.getPaqueteId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Paquete no encontrado")
    }
    override suspend fun insertarPaquete(paquete: PaqueteCreateDto): Boolean {
        Log.i("REPO", paquete.toString())
        try {
            val response = restPaquete.insertarPaquete(TokenUtils.TOKEN_CONTENT, paquete)
            Log.i("REPO", "Código de respuesta: ${response.code()}")

            if (response.isSuccessful) {
                return true
            }

            Log.i("REPO", "ErrorBody: ${response.errorBody()?.string()}")
            return false
        } catch (e: Exception) {
            Log.e("REPO", "Error al insertar paquete", e)
            return false
        }
    }
    override suspend fun modificarPaquete(paquete: PaqueteDto):
            Boolean {
        val response =
            restPaquete.actualizarPaquete(TokenUtils.TOKEN_CONTENT,
                paquete.idPaquete, paquete)
        return response.isSuccessful && response.body()?.idPaquete !=
                null
    }
}