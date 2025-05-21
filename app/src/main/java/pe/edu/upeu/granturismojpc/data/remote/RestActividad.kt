package pe.edu.upeu.granturismojpc.data.remote

import pe.edu.upeu.granturismojpc.model.ActividadCreateDto
import pe.edu.upeu.granturismojpc.model.ActividadDto
import pe.edu.upeu.granturismojpc.model.ActividadResp
import pe.edu.upeu.granturismojpc.model.Message
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestActividad {
    companion object {
        const val BASE_RUTA = "/actividad"
    }
    @GET("${BASE_RUTA}")
    suspend fun reportarActividades(@Header("Authorization")
                                  token:String): Response<List<ActividadResp>>
    @GET("${BASE_RUTA}/{id}")
    suspend fun getActividadId(@Header("Authorization")
                              token:String, @Path("id") id:Long): Response<ActividadResp>
    @DELETE("${BASE_RUTA}/{id}")
    suspend fun deleteActividad(@Header("Authorization")
                               token:String, @Path("id") id:Long): Response<Message>
    @PUT("${BASE_RUTA}/{id}")
    suspend fun actualizarActividad(@Header("Authorization")
                                   token:String, @Path("id") id:Long, @Body Actividad:
                                   ActividadDto): Response<ActividadResp>
    @POST("${BASE_RUTA}")
    suspend fun insertarActividad(@Header("Authorization")
                                 token:String, @Body Actividad: ActividadCreateDto): Response<Message>
}