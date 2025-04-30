package pe.edu.upeu.granturismojpc.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CategoryChips(navController: NavController) {
    val categories = listOf(
        "Destacado" to "destacado",
        "Hospedaje" to "ServicioHoteleraMain",
        "Artesanía" to "ServicioArtesaniamain",
        "Almuerzo" to "servicioalimentacionmain",
        "Proveedor" to "proveedormain",
        "Servicio" to "serviciomain",
        "Asocioacion" to "paquetemain"
    )

    val icons = listOf(
        Icons.Filled.Star,
        Icons.Filled.Apartment,
        Icons.Filled.ShoppingCart,
        Icons.Filled.Restaurant,
        Icons.Filled.Person,
        Icons.Filled.Construction,
        Icons.Filled.LocalConvenienceStore
    )


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp,
            alignment = Alignment.CenterHorizontally)
    ) {
        items(categories.size) { index ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { navController.navigate(categories[index].second) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFDEA8)
                    ),
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = categories[index].first,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
                Text(
                    text = categories[index].first,
                    fontSize = 12.sp
                )
            }
        }
    }
}
