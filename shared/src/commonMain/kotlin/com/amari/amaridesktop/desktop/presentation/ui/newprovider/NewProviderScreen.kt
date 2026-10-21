package com.amari.amaridesktop.desktop.presentation.ui.newprovider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amari.amaridesktop.desktop.presentation.component.Header
import com.amari.amaridesktop.desktop.presentation.ui.newprovider.event.NewProviderEvent
import org.koin.compose.koinInject

private val BgColor = Color(0xFFF8F9FA)
private val CardColor = Color.White
private val Primary = Color(0xFF0D6EFD)
private val BorderColor = Color(0xFFCED4DA)
private val BadgeBg = Color(0xFFE9ECEF)
private val Muted = Color(0xFF6C757D)
private val TextDark = Color(0xFF212529)

data class ProveedorForm(
    val nombreEmpresa: String,
    val rut: String,
    val rubro: String,
    val nombreContacto: String,
    val telefono: String,
    val email: String,
    val certificadoPath: String?,
    val contratoPath: String?
)

@Composable
fun NewProviderRoot() {
    val vm: NewProviderViewmodel = koinInject()

    NuevoProveedorScreen(onEvent = vm::onEvent)

}

@Composable
fun NuevoProveedorScreen(
    onEvent: (NewProviderEvent) -> Unit = {},
    onCancel: () -> Unit = {},
    onSave: (ProveedorForm) -> Unit = {},
    onPickCertificado: () -> String? = { null }, // devuelve la ruta del archivo elegido
    onPickContrato: () -> String? = { null },
    rubros: List<String> = listOf(
        "Ascensores",
        "Electricidad",
        "Gasfitería",
        "Climatización",
        "Seguridad",
        "Limpieza"
    )
) {
    var nombreEmpresa by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var rubro by remember { mutableStateOf("") }
    var nombreContacto by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var certificado by remember { mutableStateOf<String?>(null) }
    var contrato by remember { mutableStateOf<String?>(null) }
    var rubroExpanded by remember { mutableStateOf(false) }

    val isValid = nombreEmpresa.isNotBlank() && rut.isNotBlank() && rubro.isNotBlank() &&
            nombreContacto.isNotBlank() && telefono.isNotBlank() && email.contains("@")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)

    ) {
        // ---------- Header ----------
        Header(goBack = {
            onEvent.invoke(NewProviderEvent.GoBack())
        }, "Nuevo Proveedor")

        Text(
            "Proveedores / Nuevo",
            fontSize = 13.sp,
            color = Muted,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp)
        )
        Spacer(Modifier.height(20.dp))
        Box(Modifier.weight(1f).fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .background(CardColor, RoundedCornerShape(10.dp))
                    .verticalScroll(rememberScrollState())
                    .border(1.dp, BadgeBg, RoundedCornerShape(10.dp))
                    .padding(24.dp)

            ) {
                // ===== 1. Información General =====
                SectionHeader(
                    1,
                    "Información General",
                    "Datos legales con los que el proveedor aparecerá en los mantenimientos."
                )
                FormField(
                    "Nombre de la empresa",
                    nombreEmpresa,
                    { nombreEmpresa = it },
                    "Ej. Ascensores Schindler S.A."
                )
                FormField("RUT / NIT", rut, { rut = it }, "Ej. 12.345.678-9")

                RequiredLabel("Rubro / Especialidad")
                Box(Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        value = rubro,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona un rubro", color = Muted) },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(6.dp),
                        colors = fieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Capa transparente para abrir el menú al hacer click en todo el campo
                    Box(Modifier.matchParentSize().clickable { rubroExpanded = true })
                    DropdownMenu(
                        expanded = rubroExpanded,
                        onDismissRequest = { rubroExpanded = false }) {
                        rubros.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = { rubro = item; rubroExpanded = false }
                            )
                        }
                    }
                }

                HorizontalDivider(color = BadgeBg, modifier = Modifier.padding(vertical = 8.dp))

                // ===== 2. Contacto =====
                SectionHeader(
                    2,
                    "Contacto",
                    "Persona con quien se coordinan visitas y órdenes de trabajo."
                )
                FormField(
                    "Nombre del contacto",
                    nombreContacto,
                    { nombreContacto = it },
                    "Ej. Carolina Muñoz"
                )
                FormField(
                    "Teléfono",
                    telefono,
                    { telefono = it },
                    "+56 9 1234 5678",
                    KeyboardType.Phone
                )
                FormField(
                    "Correo electrónico",
                    email,
                    { email = it },
                    "contacto@empresa.cl",
                    KeyboardType.Email
                )

                HorizontalDivider(color = BadgeBg, modifier = Modifier.padding(vertical = 8.dp))

                // ===== 3. Documentos =====
                SectionHeader(
                    3,
                    "Documentos adjuntos (opcional)",
                    "Respaldos legales del proveedor, disponibles luego desde su ficha."
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    UploadBox(
                        title = "Certificado / RUT empresa",
                        hint = "PDF, JPG - máx 5MB",
                        fileName = certificado,
                        onClick = { onPickCertificado()?.let { certificado = it } },
                        modifier = Modifier.weight(1f)
                    )
                    UploadBox(
                        title = "Contrato",
                        hint = "PDF - máx 10MB",
                        fileName = contrato,
                        onClick = { onPickContrato()?.let { contrato = it } },
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(color = BadgeBg, modifier = Modifier.padding(vertical = 20.dp))

                // ===== Footer =====
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("* Campos obligatorios", fontSize = 12.sp, color = Muted)
                    Spacer(Modifier.weight(1f))
                    OutlinedButton(
                        onClick = onCancel,
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Muted)
                    ) { Text("Cancelar") }
                    Spacer(Modifier.width(12.dp))
                    Button(
                        onClick = {
                            onSave(
                                ProveedorForm(
                                    nombreEmpresa.trim(), rut.trim(), rubro, nombreContacto.trim(),
                                    telefono.trim(), email.trim(), certificado, contrato
                                )
                            )
                        },
                        enabled = isValid,
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Guardar Proveedor")
                    }
                }
            }
        }
    }
}

// ---------- Componentes privados del mismo screen ----------

@Composable
private fun SectionHeader(number: Int, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 16.dp)) {
        Box(
            modifier = Modifier.size(28.dp).background(BadgeBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("$number", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
            Text(subtitle, fontSize = 13.sp, color = Muted)
        }
    }
}

@Composable
private fun RequiredLabel(label: String) {
    Text(
        text = buildAnnotatedString {
            append(label)
            withStyle(SpanStyle(color = Color.Red)) { append(" *") }
        },
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = TextDark,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        RequiredLabel(label)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Muted) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(6.dp),
            colors = fieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = BorderColor,
    focusedBorderColor = Primary,
    unfocusedContainerColor = Color.White,
    focusedContainerColor = Color.White
)

@Composable
private fun UploadBox(
    title: String,
    hint: String,
    fileName: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.UploadFile
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .drawBehind {
                drawRoundRect(
                    color = BorderColor,
                    style = Stroke(
                        width = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f))
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(32.dp))
        Spacer(Modifier.height(8.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
        Text(
            text = fileName?.substringAfterLast('/')?.substringAfterLast('\\')
                ?: "Arrastra o busca archivo",
            fontSize = 13.sp,
            color = if (fileName != null) Primary else Muted
        )
        Text(hint, fontSize = 12.sp, color = Muted)
    }
}