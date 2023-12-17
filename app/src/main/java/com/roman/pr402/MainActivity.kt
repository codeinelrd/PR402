package com.roman.pr402

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF1A1A1A) // Fondo oscuro
            ) {
                ConcesionarioApp()
            }
        }
    }
}

data class Vehiculo(
    val modelo: String,
    val motor: String,
    val numAsientos: String,
    val color: String,
    val tipoVehiculo: String,
    val cargaMaxima: String,
    val numRuedas: String
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ConcesionarioApp() {
    var modelo by remember { mutableStateOf("") }
    var motor by remember { mutableStateOf("") }
    var numAsientos by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var tipoVehiculo by remember { mutableStateOf("") }
    var cargaMaxima by remember { mutableStateOf("") }
    var numRuedas by remember { mutableStateOf("") }
    var vehiculos by remember { mutableStateOf(emptyList<Vehiculo>()) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val tiposVehiculos = arrayOf("Coches", "Motos", "Patinetes", "Furgonetas", "Tráileres")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = { Text("Modelo", color = Color.White) }, // Texto blanco
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF303030)) // Fondo más oscuro
                .border(1.dp, Color(0xFF505050)) // Borde más oscuro
        )

        TextField(
            value = motor,
            onValueChange = { motor = it },
            label = { Text("Motor", color = Color.White) }, // Texto blanco
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF303030)) // Fondo más oscuro
                .border(1.dp, Color(0xFF505050)) // Borde más oscuro
        )

        TextField(
            value = numAsientos,
            onValueChange = { numAsientos = it },
            label = { Text("Número de Asientos", color = Color.White) }, // Texto blanco
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF303030)) // Fondo más oscuro
                .border(1.dp, Color(0xFF505050)) // Borde más oscuro
        )

        TextField(
            value = color,
            onValueChange = { color = it },
            label = { Text("Color", color = Color.White) }, // Texto blanco
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF303030)) // Fondo más oscuro
                .border(1.dp, Color(0xFF505050)) // Borde más oscuro
        )

        TextField(
            value = numRuedas,
            onValueChange = { numRuedas = it },
            label = { Text("Número de Ruedas", color = Color.White) }, // Texto blanco
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF303030)) // Fondo más oscuro
                .border(1.dp, Color(0xFF505050)) // Borde más oscuro
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(tiposVehiculos) { tipo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            tipoVehiculo = tipo
                        }
                ) {
                    Text(tipo, color = Color.White) // Texto blanco
                }
            }
        }

        BasicTextField(
            value = tipoVehiculo,
            onValueChange = {
                tipoVehiculo = it
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.White), // Texto blanco
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF303030)) // Fondo más oscuro
                .border(1.dp, Color(0xFF505050)) // Borde más oscuro
        )

        if (tipoVehiculo in arrayOf("Furgonetas", "Tráileres")) {
            TextField(
                value = cargaMaxima,
                onValueChange = { cargaMaxima = it },
                label = { Text("Carga Máxima", color = Color.White) }, // Texto blanco
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF303030)) // Fondo más oscuro
                    .border(1.dp, Color(0xFF505050)) // Borde más oscuro
            )
        }

        Button(
            onClick = {
                try {
                    val errorMessage = when (tipoVehiculo) {
                        "Patinetes" -> {
                            if (numAsientos.isNotBlank()) "Los patinetes no pueden tener asientos"
                            else null
                        }

                        "Tráileres" -> {
                            if (cargaMaxima.isBlank() || cargaMaxima.toIntOrNull() ?: 0 < 6 || numRuedas.toIntOrNull() ?: 0 < 6)
                                "Los tráileres deben tener al menos 6 ruedas y especificar la carga máxima"
                            else null
                        }

                        "Furgonetas" -> {
                            if (cargaMaxima.isBlank() || cargaMaxima.toIntOrNull() ?: 0 > 6 || numRuedas.toIntOrNull() ?: 0 > 6)
                                "Las furgonetas deben tener como máximo 6 ruedas y especificar la carga máxima"
                            else null
                        }

                        "Motos" -> {
                            if (numAsientos.toIntOrNull() ?: 0 > 2) "Las motos no pueden tener más de dos asientos"
                            else null
                        }

                        else -> null
                    }

                    if (errorMessage == null) {
                        val nuevoVehiculo = Vehiculo(
                            modelo,
                            motor,
                            numAsientos,
                            color,
                            tipoVehiculo,
                            cargaMaxima,
                            numRuedas
                        )
                        vehiculos = vehiculos + nuevoVehiculo

                        modelo = ""
                        motor = ""
                        numAsientos = ""
                        color = ""
                        tipoVehiculo = ""
                        cargaMaxima = ""
                        numRuedas = ""
                    } else {
                        println("Error: $errorMessage")
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Crear Vehículo", color = Color.White) // Texto blanco
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Vehículos Creados:", color = Color.White) // Texto blanco
        LazyColumn {
            items(vehiculos) { vehiculo ->
                val cargaMaximaText = if (vehiculo.tipoVehiculo in arrayOf("Furgonetas", "Tráileres")) {
                    " - Carga Máxima: ${vehiculo.cargaMaxima}"
                } else {
                    ""
                }
                Text("${vehiculo.tipoVehiculo} - ${vehiculo.modelo} - ${vehiculo.motor} - ${vehiculo.numRuedas} ruedas - ${vehiculo.color}  ${cargaMaximaText}", color = Color.White) // Texto blanco
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConcesionarioApp()
}

