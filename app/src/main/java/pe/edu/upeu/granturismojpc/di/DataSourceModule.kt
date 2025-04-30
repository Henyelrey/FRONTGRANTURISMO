package pe.edu.upeu.granturismojpc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pe.edu.upeu.granturismojpc.data.remote.RestDestino
import pe.edu.upeu.granturismojpc.data.remote.RestPaquete
import pe.edu.upeu.granturismojpc.data.remote.RestProveedor
import pe.edu.upeu.granturismojpc.data.remote.RestServicio
import pe.edu.upeu.granturismojpc.data.remote.RestServicioAlimentacion
import pe.edu.upeu.granturismojpc.data.remote.RestServicioArtesania
import pe.edu.upeu.granturismojpc.data.remote.RestServicioHotelera
import pe.edu.upeu.granturismojpc.data.remote.RestTipoServicio
import pe.edu.upeu.granturismojpc.data.remote.RestUsuario
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    var retrofit: Retrofit?=null
    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl()= TokenUtils.API_URL
    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl:String):
            Retrofit {
        val okHttpClient= OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        if (retrofit==null){
            retrofit= Retrofit.Builder()

                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl).build()
        }
        return retrofit!!
    }
    @Singleton
    @Provides
    fun restUsuario(retrofit: Retrofit): RestUsuario{
        return retrofit.create(RestUsuario::class.java)
    }

    @Singleton
    @Provides
    fun restPaquete(retrofit: Retrofit): RestPaquete{
        return retrofit.create(RestPaquete::class.java)
    }

    @Singleton
    @Provides
    fun restProveedor(retrofit: Retrofit): RestProveedor{
        return retrofit.create(RestProveedor::class.java)
    }

    @Singleton
    @Provides
    fun restDestino(retrofit: Retrofit): RestDestino{
        return retrofit.create(RestDestino::class.java)
    }
    @Singleton
    @Provides
    fun restServicio(retrofit: Retrofit): RestServicio{
        return retrofit.create(RestServicio::class.java)
    }
    @Singleton
    @Provides
    fun restTipoServicio(retrofit: Retrofit): RestTipoServicio{
        return retrofit.create(RestTipoServicio::class.java)
    }

    @Singleton
    @Provides
    fun restTipoServicioAlimentacion(retrofit: Retrofit): RestServicioAlimentacion{
        return retrofit.create(RestServicioAlimentacion::class.java)
    }
    @Singleton
    @Provides
    fun restTipoServicioArtesania(retrofit: Retrofit): RestServicioArtesania{
        return retrofit.create(RestServicioArtesania::class.java)
    }

    @Singleton
    @Provides
    fun restTipoServicioHotelera(retrofit: Retrofit): RestServicioHotelera{
        return retrofit.create(RestServicioHotelera::class.java)
    }

}




// inyecta automáticamente el cliente de API usando retrofit