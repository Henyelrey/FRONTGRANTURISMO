package pe.edu.upeu.granturismojpc.repository

import jakarta.inject.Inject
import pe.edu.upeu.granturismojpc.data.remote.RestDestino
import pe.edu.upeu.granturismojpc.model.DestinoResp
import pe.edu.upeu.granturismojpc.utils.TokenUtils

interface DestinoRepository {
    suspend fun findAll(): List<DestinoResp>
}
class DestinoRepositoryImp @Inject constructor(
    private val rest: RestDestino,
): DestinoRepository{
    override suspend fun findAll(): List<DestinoResp> {
        val response =
            rest.reportarDestinos(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }
}