package pe.edu.upeu.granturismojpc.model

data class UsuarioDto(
    var user: String,
    var clave: String,
)
data class UsuarioResp(
    val idUsuario: Long,
    val user: String,
    val token: String,
)
data class UsuarioRegisterDto(
    val user: String,
    val clave: String,
    val rol: String,
    val estado: String
)