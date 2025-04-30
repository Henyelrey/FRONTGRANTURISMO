package pe.edu.upeu.granturismojpc.ui.presentation.screens.register

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upeu.granturismojpc.model.UsuarioDto
import pe.edu.upeu.granturismojpc.model.UsuarioRegisterDto
import pe.edu.upeu.granturismojpc.ui.presentation.components.ImageLogin
import pe.edu.upeu.granturismojpc.ui.presentation.components.ErrorImageAuth
import pe.edu.upeu.granturismojpc.ui.presentation.components.ProgressBarLoading
import pe.edu.upeu.granturismojpc.ui.theme.LightRedColors
import pe.edu.upeu.granturismojpc.utils.ComposeReal
import pe.edu.upeu.granturismojpc.utils.TokenUtils
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.EmailTextField
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.LoginButton
import pe.edu.upeu.granturismojpc.ui.presentation.components.form.PasswordTextField
import pe.edu.upeu.granturismojpc.ui.theme.GranTurismoJPCTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isRegistered by viewModel.isRegistered.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLogin()
        Text("Crear cuenta", fontSize = 20.sp)
        BuildEasyForms { easyForm ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                EmailTextField(easyForms = easyForm, text = "", label = "E-Mail:", "U")
                PasswordTextField(easyForms = easyForm, text = "", label = "Contraseña:", key = "password")
                PasswordTextField(easyForms = easyForm, text = "", label = "Confirmar contraseña:", key = "confirmPassword")
                Text(
                    text = "La contraseña debe tener entre 8 a 20 caracteres, contener al menos una letra minúscula, una mayúscula, un número y un carácter especial (+ * , .)",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                LoginButton(
                    easyForms = easyForm,
                    onClick = {
                        val dataForm = easyForm.formData()
                        val email = (dataForm[0] as EasyFormsResult.StringResult).value
                        val password = (dataForm[1] as EasyFormsResult.StringResult).value
                        val confirmPassword = (dataForm[2] as EasyFormsResult.StringResult).value

                        if (password != confirmPassword) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Las contraseñas no coinciden")
                            }
                        } else {
                            val newUser = UsuarioRegisterDto(
                                user = email,
                                clave = password,
                                rol = "USER",
                                estado = "Activo"
                            )
                            viewModel.registerUser(newUser)
                        }
                    },
                    label = "Registrarse"
                )

                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { navigateToLogin() }) {
                    Text("¿Ya tienes cuenta? Iniciar sesión")
                }
            }
        }

        ErrorImageAuth(isImageValidate = isError)
        ProgressBarLoading(isLoading = isLoading)
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .wrapContentHeight(Alignment.Bottom)
            .padding(16.dp)
    )

    // Navegar a Login si se registró correctamente
    LaunchedEffect(isRegistered) {
        if (isRegistered) {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
            navigateToLogin()
        }
    }

    // Mostrar errores en Snackbar
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearErrorMessage()
        }
    }
}

