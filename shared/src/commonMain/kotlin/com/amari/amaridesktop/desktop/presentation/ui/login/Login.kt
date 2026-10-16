package com.amari.amaridesktop.desktop.presentation.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.presentation.component.LoginLoaderOverlay
import com.amari.amaridesktop.desktop.theme.Colors

// ==========================================
// PANTALLA
// ==========================================

@Composable
fun LoginScreen(
    onIniciarSesion: (email: String, contrasena: String) -> Unit,
    onOlvideContrasena: () -> Unit,
    cargando: Boolean = false,
    errorServidor: String? = null,
    edificio: String = "Edificio Torre Norte",
    version: String = "v1.0",
    anio: Int = 2025
) {
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var verContrasena by remember { mutableStateOf(false) }
    var intentoEnviar by remember { mutableStateOf(false) }

    val errorEmail = when {
        !intentoEnviar -> null
        email.isBlank() -> "Ingresa tu email"
        !esEmailValido(email) -> "Email no válido"
        else -> null
    }
    val errorContrasena =
        if (intentoEnviar && contrasena.isBlank()) "Ingresa tu contraseña" else null

    val enviar: () -> Unit = {
        intentoEnviar = true
        if (!cargando && esEmailValido(email) && contrasena.isNotBlank()) {
            onIniciarSesion(email.trim(), contrasena)
        }
    }
    Box(Modifier.fillMaxSize()) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.fondo)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 420.dp).fillMaxWidth()
            ) {

                // ---------- Branding ----------
                Box(
                    Modifier.size(64.dp).background(Colors.primario, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Apartment,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Sistema de Mantenimiento",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.texto
                )
                Text(edificio, fontSize = 14.sp, color = Colors.textoSecundario)

                Spacer(Modifier.height(28.dp))

                // ---------- Tarjeta ----------
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Colors.superficie,
                    border = BorderStroke(1.dp, Colors.borde),
                    shadowElevation = 2.dp
                ) {
                    Column(Modifier.padding(28.dp)) {
                        Text(
                            "Iniciar Sesión",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Colors.texto
                        )
                        Spacer(Modifier.height(24.dp))

                        // Email
                        EtiquetaCampo("Email")
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("usuario@edificio.com.pe") },
                            leadingIcon = { Icon(Icons.Default.Email, null) },
                            singleLine = true,
                            enabled = !cargando,
                            isError = errorEmail != null,
                            supportingText = errorEmail?.let { { Text(it) } },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(10.dp),
                            colors = coloresCampo()
                        )

                        Spacer(Modifier.height(12.dp))

                        // Contraseña
                        EtiquetaCampo("Contraseña")
                        OutlinedTextField(
                            value = contrasena,
                            onValueChange = { contrasena = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("••••••••") },
                            leadingIcon = { Icon(Icons.Default.Lock, null) },
                            trailingIcon = {
                                IconButton(onClick = { verContrasena = !verContrasena }) {
                                    Icon(
                                        if (verContrasena) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (verContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                                    )
                                }
                            },
                            visualTransformation = if (verContrasena) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            singleLine = true,
                            enabled = !cargando,
                            isError = errorContrasena != null,
                            supportingText = errorContrasena?.let { { Text(it) } },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = { enviar() }),
                            shape = RoundedCornerShape(10.dp),
                            colors = coloresCampo()
                        )

                        // Olvidé contraseña
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                            TextButton(onClick = onOlvideContrasena, enabled = !cargando) {
                                Text(
                                    "¿Olvidaste tu contraseña?",
                                    fontSize = 13.sp,
                                    color = Colors.primario
                                )
                            }
                        }

                        // Error del servidor
                        if (errorServidor != null) {
                            Surface(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Colors.urgente.copy(alpha = 0.08f),
                                border = BorderStroke(1.dp, Colors.urgente.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    errorServidor,
                                    modifier = Modifier.padding(12.dp),
                                    fontSize = 13.sp,
                                    color = Colors.urgente
                                )
                            }
                        }

                        // Botón
                        Button(
                            onClick = enviar,
                            enabled = !cargando,
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Colors.primario)
                        ) {
                            if (cargando) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    "Iniciar Sesión",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // ---------- Footer ----------
                Text(
                    "Sistema de Mantenimiento $version · $edificio · $anio",
                    fontSize = 12.sp,
                    color = Colors.textoSecundario,
                    textAlign = TextAlign.Center
                )
            }
        }

        LoginLoaderOverlay(visible = cargando)
    }
}

// ==========================================
// COMPONENTES / UTILIDADES
// ==========================================

@Composable
private fun EtiquetaCampo(texto: String) {
    Text(
        texto,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Colors.texto,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Colors.primario,
    unfocusedBorderColor = Colors.borde,
    focusedLeadingIconColor = Colors.primario,
    unfocusedLeadingIconColor = Colors.textoSecundario,
    cursorColor = Colors.primario
)

private fun esEmailValido(email: String): Boolean =
    Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(email.trim())

// ==========================================
// PRUEBA
// ==========================================

@Composable
fun LoginScreenPrueba() {
    var cargando by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    MaterialTheme {
        LoginScreen(
            cargando = cargando,
            errorServidor = error,
            onIniciarSesion = { email, contrasena ->
                println("Login: $email")
                error = if (contrasena == "1234") null else "Email o contraseña incorrectos"
            },
            onOlvideContrasena = { println("Olvidé contraseña") }
        )
    }
}