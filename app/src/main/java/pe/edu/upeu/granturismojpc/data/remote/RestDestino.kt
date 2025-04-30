package pe.edu.upeu.granturismojpc.data.remote

import pe.edu.upeu.granturismojpc.model.Message
import pe.edu.upeu.granturismojpc.model.DestinoCreateDto
import pe.edu.upeu.granturismojpc.model.DestinoDto
import pe.edu.upeu.granturismojpc.model.DestinoResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestDestino {
    companion object {
        const val BASE_RUTA = "/destinos"
    }
    @GET("${BASE_RUTA}")
    suspend fun reportarDestinos(@Header("Authorization")
                                    token:String): Response<List<DestinoResp>>
    @GET("${BASE_RUTA}/{id}")
    suspend fun getDestinoId(@Header("Authorization")
                               token:String, @Path("id") id:Long): Response<DestinoResp>
    @DELETE("${BASE_RUTA}/{id}")
    suspend fun deleteDestino(@Header("Authorization")
                                token:String, @Path("id") id:Long): Response<Message>
    @PUT("${BASE_RUTA}/{id}")
    suspend fun actualizarDestino(@Header("Authorization")
                                    token:String, @Path("id") id:Long, @Body Destino:
                                    DestinoDto): Response<DestinoResp>
    @POST("${BASE_RUTA}")
    suspend fun insertarDestino(@Header("Authorization")
                                  token:String, @Body Destino: DestinoCreateDto): Response<Message>
}