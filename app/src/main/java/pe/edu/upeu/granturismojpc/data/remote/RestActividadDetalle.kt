package pe.edu.upeu.granturismojpc.data.remote

import pe.edu.upeu.granturismojpc.model.ActividadDetalleCreateDto
import pe.edu.upeu.granturismojpc.model.ActividadDetalleDto
import pe.edu.upeu.granturismojpc.model.ActividadDetalleResp
import pe.edu.upeu.granturismojpc.model.Message
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestActividadDetalle {
    companion object {
        const val BASE_RUTA = "/actividaddetalle"
    }
    @GET("${BASE_RUTA}")
    suspend fun reportarActividadDetalle(@Header("Authorization")
                                    token:String): Response<List<ActividadDetalleResp>>
    @GET("${BASE_RUTA}/{id}")
    suspend fun getActividadDetalleId(@Header("Authorization")
                               token:String, @Path("id") id:Long): Response<ActividadDetalleResp>
    @DELETE("${BASE_RUTA}/{id}")
    suspend fun deleteActividadDetalle(@Header("Authorization")
                                token:String, @Path("id") id:Long): Response<Message>
    @PUT("${BASE_RUTA}/{id}")
    suspend fun actualizarActividadDetalle(@Header("Authorization")
                                    token:String, @Path("id") id:Long, @Body ActividadDetalle:
                                    ActividadDetalleDto): Response<ActividadDetalleResp>
    @POST("${BASE_RUTA}")
    suspend fun insertarActividadDetalle(@Header("Authorization")
                                  token:String, @Body ActividadDetalle: ActividadDetalleCreateDto): Response<Message>
}