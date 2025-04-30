package pe.edu.upeu.granturismojpc.model

data class DestinoDto(
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

