package pe.edu.upeu.granturismojpc.data.remote

import pe.edu.upeu.granturismojpc.model.Message
import pe.edu.upeu.granturismojpc.model.PaqueteCreateDto
import pe.edu.upeu.granturismojpc.model.PaqueteDto
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestPaquete {
    @GET("${BASE_PACK}")
    suspend fun reportarPaquete(@Header("Authorization")
                                 token:String): Response<List<PaqueteResp>>
    @GET("${BASE_PACK}/{id}")
    suspend fun getPaqueteId(@Header("Authorization")
                              token:String, @Path("id") id:Long): Response<PaqueteResp>
    @DELETE("${BASE_PACK}/{id}")
    suspend fun deletePaquete(@Header("Authorization")
                               token:String, @Path("id") id:Long): Response<Message>
    @PUT("${BASE_PACK}/{id}")
    suspend fun actualizarPaquete(@Header("Authorization")
                                   token:String, @Path("id") id:Long, @Body Paquete:
                                   PaqueteDto): Response<PaqueteResp>
    @POST("${BASE_PACK}")
    suspend fun insertarPaquete(@Header("Authorization")
                                 token:String, @Body Paquete: PaqueteCreateDto): Response<Message>
    companion object {
        const val BASE_PACK = "/paquetes"
    }
}