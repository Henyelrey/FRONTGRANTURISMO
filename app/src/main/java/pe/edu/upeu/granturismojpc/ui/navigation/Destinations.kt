package pe.edu.upeu.granturismojpc.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Login : Destinations("login", "Login", Icons.Filled.Settings)
    object Register : Destinations("register", "Registro", Icons.Filled.Favorite)
    object Pantalla1 : Destinations("pantalla1", "Pantalla 1", Icons.Filled.Home)
    object Pantalla2 : Destinations("pantalla2/?newText={newText}", "Pantalla 2", Icons.Filled.Settings) {
        fun createRoute(newText: String) = "pantalla2/?newText=$newText"
    }

    object Pantalla3 : Destinations("pantalla3", "Pantalla 3", Icons.Filled.Favorite)
    object Pantalla4 : Destinations("pantalla4", "Pantalla 4x", Icons.Filled.Face)
    object Pantalla5 : Destinations("pantalla5", "Pantalla 5x", Icons.Filled.AccountCircle)

    object HomeScreen : Destinations("pantallahome", "Pantalla Home", Icons.Filled.Home)

    object PaqueteMainSC: Destinations("paquetemain","Adm. Asociaciones", Icons.Filled.DateRange)
        object PaqueteFormSC:
            Destinations("paqueteForm?prodId={prodId}", "Form Paquete",
                Icons.Filled.Add){
            fun passId(prodId:String?):String{
                return "paqueteForm?prodId=$prodId"
            }
        }

    object ProveedorMainSC: Destinations("proveedormain","Adm. Proveedor", Icons.Filled.DateRange)
    object ProveedorFormSC:
        Destinations("proveedorForm?provId={provId}", "Form Proveedor",
            Icons.Filled.Add){
        fun passId(provId:String?):String{
            return "proveedorForm?provId=$provId"
        }
    }
    object ServicioMainSC: Destinations("serviciomain","Adm. Servicio", Icons.Filled.DateRange)
    object ServicioFormSC:
        Destinations("servicioForm?servId={servId}", "Form Servicio",
            Icons.Filled.Add){
        fun passId(servId:String?):String{
            return "servicioForm?servId=$servId"
        }
    }
    object ServicioAlimentacionMainSC: Destinations("servicioalimentacionmain","Adm. Servicio Alimentacion", Icons.Filled.DateRange)
    object ServicioAlimentacionFormSC:
        Destinations("servicioAlimentacionForm?servaliId={servaliId}", "Form Servicio Alimentacion",
            Icons.Filled.Add){
        fun passId(servaliId:String?):String{
            return "servicioAlimentacionForm?servaliId=$servaliId"
        }
    }

    object ServicioArtesaniaMainSC: Destinations("ServicioArtesaniamain","Adm. Servicio Artesania", Icons.Filled.DateRange)
    object ServicioArtesaniaFormSC:
        Destinations("ServicioArtesaniaForm?servartId={servartId}", "Form Servicio Artesania",
            Icons.Filled.Add){
        fun passId(servartId:String?):String{
            return "ServicioArtesaniaForm?servartId=$servartId"
        }
    }

    object ServicioHoteleraMainSC: Destinations("ServicioHoteleraMain","Adm. Servicio Hotelería", Icons.Filled.DateRange)
    object ServicioHoteleraFormSC:
        Destinations("ServicioHoteleraForm?servhotId={servhotId}", "Form Servicio Hotelería",
            Icons.Filled.Add){
        fun passId(servhotId:String?):String{
            return "ServicioHoteleraForm?servhotId=$servhotId"
        }
    }
}