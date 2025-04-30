package pe.edu.upeu.granturismojpc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upeu.granturismojpc.repository.DestinoRepository
import pe.edu.upeu.granturismojpc.repository.DestinoRepositoryImp
import pe.edu.upeu.granturismojpc.repository.PaqueteRepository
import pe.edu.upeu.granturismojpc.repository.PaqueteRepositoryImp
import pe.edu.upeu.granturismojpc.repository.ProveedorRepository
import pe.edu.upeu.granturismojpc.repository.ProveedorRepositoryImp
import pe.edu.upeu.granturismojpc.repository.ServicioAlimentacionRepository
import pe.edu.upeu.granturismojpc.repository.ServicioAlimentacionRepositoryImp
import pe.edu.upeu.granturismojpc.repository.ServicioArtesaniaRepository
import pe.edu.upeu.granturismojpc.repository.ServicioArtesaniaRepositoryImp
import pe.edu.upeu.granturismojpc.repository.ServicioHoteleraRepository
import pe.edu.upeu.granturismojpc.repository.ServicioHoteleraRepositoryImp
import pe.edu.upeu.granturismojpc.repository.ServicioRepository
import pe.edu.upeu.granturismojpc.repository.ServicioRepositoryImp
import pe.edu.upeu.granturismojpc.repository.TipoServicioRepository
import pe.edu.upeu.granturismojpc.repository.TipoServicioRepositoryImp
import pe.edu.upeu.granturismojpc.repository.UsuarioRepository
import pe.edu.upeu.granturismojpc.repository.UsuarioRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun userRepository(userRepos:
                                    UsuarioRepositoryImp): UsuarioRepository
    @Binds
    @Singleton
    abstract fun paqueteRepository(packRepos:
                                PaqueteRepositoryImp): PaqueteRepository
    @Binds
    @Singleton
    abstract fun proveedorRepository(provRepos:
                                   ProveedorRepositoryImp): ProveedorRepository
    @Binds
    @Singleton
    abstract fun destinoRepository(destinoRepos:
                                         DestinoRepositoryImp): DestinoRepository


    @Binds
    @Singleton
    abstract fun servicioRepository(servRepos:
                                     ServicioRepositoryImp): ServicioRepository
    @Binds
    @Singleton
    abstract fun tipoServicioRepository(tiposervRepos:
                                     TipoServicioRepositoryImp): TipoServicioRepository

    @Binds
    @Singleton
    abstract fun servicioAliRepository(servaliRepos:
                                        ServicioAlimentacionRepositoryImp): ServicioAlimentacionRepository

    @Binds
    @Singleton
    abstract fun servicioArtRepository(servartRepos:
                                       ServicioArtesaniaRepositoryImp): ServicioArtesaniaRepository


    //configura Dagger-Hilt para que cuando pidas un ServicioHoteleraRepository
    // te ntregue un ServicioHoteleraRepositoryImp ya construido
    @Binds
    @Singleton
    abstract fun servicioHotelRepository(servhotRepos:
                                       ServicioHoteleraRepositoryImp): ServicioHoteleraRepository


}
