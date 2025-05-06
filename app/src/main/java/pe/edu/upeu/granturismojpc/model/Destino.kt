package pe.edu.upeu.granturismojpc.model

data class DestinoDto(
    var idDestino: Long,
    var nombre: String,
    var descripcion: String,
    var ubicacion: String,
    var imagenUrl: String,
    var latitud: Double,
    var longitud: Double,
    var popularidad: Int,
    var precioMedio: Double,
    var rating: Double,
)

data class DestinoCreateDto(
    val nombre: String,
    val descripcion: String,
    val ubicacion: String,
    val imagenUrl: String,
    val latitud: Double,
    val longitud: Double,
    val popularidad: Int,
    val precioMedio: Double,
    val rating: Double,
)

data class DestinoResp(
    val idDestino: Long,
    val nombre: String,
    val descripcion: String,
    val ubicacion: String,
    val imagenUrl: String,
    val latitud: Double,
    val longitud: Double,
    val popularidad: Int,
    val precioMedio: Double,
    val rating: Double,
)

fun DestinoResp.toDto(): DestinoDto{
    return DestinoDto(
        idDestino = this.idDestino,
        nombre = this.nombre,
        descripcion = this.descripcion,
        ubicacion = this.ubicacion,
        imagenUrl = this.imagenUrl,
        latitud = this.latitud,
        longitud = this.longitud,
        popularidad = this.popularidad,
        precioMedio = this.precioMedio,
        rating = this.rating,
    )
}

fun DestinoDto.toCreateDto(): DestinoCreateDto {
    return DestinoCreateDto(
        nombre = this.nombre,
        descripcion = this.descripcion,
        ubicacion = this.ubicacion,
        imagenUrl = this.imagenUrl,
        latitud = this.latitud,
        longitud = this.longitud,
        popularidad = this.popularidad,
        precioMedio = this.precioMedio,
        rating = this.rating,
    )
}
