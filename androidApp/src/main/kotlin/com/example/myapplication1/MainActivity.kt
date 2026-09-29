package com.example.myapplication1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Calendar

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Formulario()
            }
        }
    }
}

@Composable
fun Formulario() {

    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    var mostrarCard by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Registro de tarea",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // NOMBRE
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                mostrarCard = false
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // MATRÍCULA
        OutlinedTextField(
            value = matricula,
            onValueChange = {
                if (it.all { caracter -> caracter.isDigit() }) {
                    matricula = it
                }
                mostrarCard = false
            },
            label = {
                Text("Matrícula")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ASIGNATURA
        OutlinedTextField(
            value = asignatura,
            onValueChange = {
                asignatura = it
                mostrarCard = false
            },
            label = {
                Text("Asignatura")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // HORA
        OutlinedTextField(
            value = hora,
            onValueChange = {},
            label = {
                Text("Hora que se imparte")
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

                val timePicker = TimePickerDialog(
                    context,
                    { _, hour, minute ->

                        hora = String.format(
                            "%02d:%02d",
                            hour,
                            minute
                        )

                        mostrarCard = false
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )

                timePicker.show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar hora")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // FECHA
        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            label = {
                Text("Fecha de entrega")
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, day ->

                        fecha = String.format(
                            "%02d/%02d/%04d",
                            day,
                            month + 1,
                            year
                        )

                        mostrarCard = false
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                datePicker.show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar fecha")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // MENSAJE DE ERROR
        if (error.isNotEmpty()) {

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // BOTÓN GUARDAR
        Button(
            onClick = {

                if (
                    nombre.isBlank() ||
                    matricula.isBlank() ||
                    asignatura.isBlank() ||
                    hora.isBlank() ||
                    fecha.isBlank()
                ) {

                    error = "Completa todos los campos."
                    mostrarCard = false

                } else {

                    error = ""
                    mostrarCard = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CARD
        if (mostrarCard) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Datos registrados",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Nombre: $nombre")

                    Text("Matrícula: $matricula")

                    Text("Asignatura: $asignatura")

                    Text("Hora que se imparte: $hora")

                    Text("Fecha de entrega: $fecha")
                }
            }
        }
    }
}