package pe.edu.upeu.granturismojpc.model

data class ProveedorDto(
    var idProveedor: Long,
    var nombreCompleto: String,
    var email: String,
    var telefono: String,
    var fechaRegistro: String,
    var usuario: Long
)

data class ProveedorResp(
    val idProveedor: Long,
    val nombreCompleto: String,
    val email: String,
    val telefono: String,
    val fechaRegistro: String,
    val usuario: UsuarioResp
)

data class ProveedorCreateDto(
    val nombreCompleto: String,
    val email: String,
    val telefono: String,
    val fechaRegistro: String,
    val usuario: Long
)

fun ProveedorResp.toDto(): ProveedorDto {
    return ProveedorDto(
        idProveedor = this.idProveedor,
        nombreCompleto = this.nombreCompleto,
        email = this.email,
        telefono = this.telefono,
        fechaRegistro = this.fechaRegistro,
        usuario = this.usuario.idUsuario
    )
}
fun ProveedorDto.toCreateDto(): ProveedorCreateDto {
    return ProveedorCreateDto(
        nombreCompleto = this.nombreCompleto,
        email = this.email,
        telefono = this.telefono,
        fechaRegistro = this.fechaRegistro,
        usuario = this.usuario
    )
}

