package pe.edu.upeu.granturismojpc.model

import java.math.BigDecimal
import java.time.LocalDate

data class PaqueteDto(
    var idPaquete: Long,
    var titulo: String,
    var descripcion: String,
    var precioTotal: Double,
    var imagenUrl: String,
    var estado: String,
    var duracionDias: Int,
    var localidad: String,
    var tipoActividad: String,
    var cuposMaximos: Int,
    var proveedor: Long,
    var fechaInicio: String,
    var fechaFin: String,
    var destino: Long,
)

data class PaqueteResp(
    val idPaquete: Long,
    val titulo: String,
    val descripcion: String,
    val precioTotal: BigDecimal,
    val imagenUrl: String,
    var estado: String,
    var duracionDias: Int,
    val localidad: String,
    val tipoActividad: String,
    val cuposMaximos: Int,
    val proveedor: ProveedorResp,
    val fechaInicio: String,
    val fechaFin: String,
    var destino: DestinoResp,
)

data class PaqueteCreateDto(
    val titulo: String,
    val descripcion: String,
    val precioTotal: Double,
    val imagenUrl: String,
    var estado: String,
    var duracionDias: Int,
    val localidad: String,
    val tipoActividad: String,
    val cuposMaximos: Int,
    val proveedor: Long,
    val fechaInicio: String,
    val fechaFin: String,
    var destino: Long,
)

fun PaqueteResp.toDto(): PaqueteDto {
    return PaqueteDto(
        idPaquete = this.idPaquete,
        titulo = this.titulo,
        descripcion = this.descripcion,
        precioTotal = this.precioTotal.toDouble(),
        imagenUrl = this.imagenUrl,
        estado = this.estado,
        duracionDias = this.duracionDias,
        localidad = this.localidad,
        tipoActividad = this.tipoActividad,
        cuposMaximos = this.cuposMaximos,
        proveedor = this.proveedor.idProveedor,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        destino = this.destino.idDestino
    )
}

fun PaqueteDto.toCreateDto(): PaqueteCreateDto {
    return PaqueteCreateDto(
        titulo = this.titulo,
        descripcion = this.descripcion,
        precioTotal = this.precioTotal,
        imagenUrl = this.imagenUrl,
        estado = this.estado,
        duracionDias = this.duracionDias,
        localidad = this.localidad,
        tipoActividad = this.tipoActividad,
        cuposMaximos = this.cuposMaximos,
        proveedor = this.proveedor,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        destino = this.destino
    )
}