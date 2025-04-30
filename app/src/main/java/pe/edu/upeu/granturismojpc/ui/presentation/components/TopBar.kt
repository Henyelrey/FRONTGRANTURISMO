package pe.edu.upeu.granturismojpc.ui.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    titulo: String = "Gran Turismo",
    scope: CoroutineScope,
    scaffoldState: DrawerState,
    openDialog: () -> Unit,
    displaySnackBar: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = titulo)
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menú"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                displaySnackBar()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            }
            IconButton(onClick = {
                openDialog()
            }) {
                Icon(
                    imageVector = Icons.Default.Settings, // icono de filtros
                    contentDescription = "Filtros"
                )
            }
        }
    )
}