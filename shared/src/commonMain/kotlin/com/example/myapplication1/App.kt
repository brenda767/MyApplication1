package com.example.myapplication1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.json.JSONObject

@Composable
fun App() {
    // Estados para los campos
    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // Estados para los errores de validación
    var errorNombre by remember { mutableStateOf(false) }
    var errorMatricula by remember { mutableStateOf(false) }
    var errorAsignatura by remember { mutableStateOf(false) }
    var errorHora by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }

    // Estado del JSON guardado y visibilidad del Card
    var jsonGuardado by remember { mutableStateOf<JSONObject?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Asignatura",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo: Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                if (it.isNotBlank()) errorNombre = false
            },
            label = { Text("Nombre completo") },
            isError = errorNombre,
            supportingText = { if (errorNombre) Text("El nombre es obligatorio") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Campo: Matrícula
        OutlinedTextField(
            value = matricula,
            onValueChange = {
                matricula = it
                if (it.isNotBlank()) errorMatricula = false
            },
            label = { Text("Matrícula") },
            isError = errorMatricula,
            supportingText = { if (errorMatricula) Text("La matrícula es obligatoria") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Campo: Asignatura
        OutlinedTextField(
            value = asignatura,
            onValueChange = {
                asignatura = it
                if (it.isNotBlank()) errorAsignatura = false
            },
            label = { Text("Asignatura") },
            isError = errorAsignatura,
            supportingText = { if (errorAsignatura) Text("La asignatura es obligatoria") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Campo: Hora que se imparte
        OutlinedTextField(
            value = hora,
            onValueChange = {
                hora = it
                if (it.isNotBlank()) errorHora = false
            },
            label = { Text("Hora que se imparte (ej. 10:00)") },
            isError = errorHora,
            supportingText = { if (errorHora) Text("La hora es obligatoria") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Campo: Fecha de entrega
        OutlinedTextField(
            value = fecha,
            onValueChange = {
                fecha = it
                if (it.isNotBlank()) errorFecha = false
            },
            label = { Text("Fecha de entrega (ej. 25/10/2026)") },
            isError = errorFecha,
            supportingText = { if (errorFecha) Text("La fecha es obligatoria") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Botón Guardar
        Button(
            onClick = {
                // Validación
                errorNombre = nombre.isBlank()
                errorMatricula = matricula.isBlank()
                errorAsignatura = asignatura.isBlank()
                errorHora = hora.isBlank()
                errorFecha = fecha.isBlank()

                if (!errorNombre && !errorMatricula && !errorAsignatura && !errorHora && !errorFecha) {
                    // Creación del JSON temporal
                    jsonGuardado = JSONObject().apply {
                        put("nombre", nombre)
                        put("matricula", matricula)
                        put("asignatura", asignatura)
                        put("hora", hora)
                        put("fecha", fecha)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Guardar Datos")
        }

        // Card inferior para mostrar los datos almacenados
        jsonGuardado?.let { json ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Datos Guardados (JSON Temporal)",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text("Nombre: ${json.optString("nombre")}")
                    Text("Matrícula: ${json.optString("matricula")}")
                    Text("Asignatura: ${json.optString("asignatura")}")
                    Text("Hora: ${json.optString("hora")}")
                    Text("Fecha: ${json.optString("fecha")}")
                }
            }
        }
    }
}