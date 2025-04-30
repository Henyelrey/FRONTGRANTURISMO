package pe.edu.upeu.granturismojpc.ui.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pe.edu.upeu.granturismojpc.model.DestinoResp
import pe.edu.upeu.granturismojpc.model.PaqueteResp
import pe.edu.upeu.granturismojpc.model.ProveedorResp
//import pe.edu.upeu.granturismojpc.data.remote.response.ProveedorResp
import java.math.BigDecimal

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

@Composable
fun PaqueteCard(
    paquete: PaqueteResp,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Imagen a la izquierda
                AsyncImage(
                    model = paquete.imagenUrl,
                    contentDescription = "Imagen del paquete",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Título a la derecha
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alignByBaseline()
                ) {
                    Text(
                        text = paquete.titulo,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    /*Text(
                        text = paquete.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(4.dp))*/

                    //Text(text = "Precio: S/ ${paquete.precioTotal}")
                    //Text(text = "Duración: ${paquete.duracionDias} días")
                    Text(text = "Localidad: ${paquete.localidad}")
                    Text(text = "Actividad: ${paquete.tipoActividad}")
                    //Text(text = "Cupos: ${paquete.cuposMaximos}")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))



            // Botones estilo fila inferior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón de Detalles
                Button (
                    onClick = { /* Acción de detalles */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA726), // Naranja (opcional)
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Detalles",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Detalles")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón de WhatsApp
                Button(
                    onClick = { /* Acción de WhatsApp */ },
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366) // Color verde de WhatsApp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Phone, // Puedes usar otro ícono si deseas
                        contentDescription = "WhatsApp",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
