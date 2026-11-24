package com.pizzeriaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PizzeriaScreen(
    viewModel: PizzeriaViewModel,
    modifier: Modifier = Modifier
) {
    // 1. Observamos los LiveData del ViewModel (se actualizan solos al cambiar)
    val clientes by viewModel.clientes.observeAsState(emptyList())
    val pedidos by viewModel.pedidos.observeAsState(emptyList())
    val ultimoCliente by viewModel.ultimoCliente.observeAsState("Ninguno")
    val mensajeEstado by viewModel.mensajeEstado.observeAsState("")

    // 2. Estado local del formulario
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // TÍTULO
        item {
            Text(
                text = "PizzeriaApp — Panel Principal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Bienvenidos a la mejor pizzería de la ciudad",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // BLOQUE 1: MENSAJE DE RESUMEN DINÁMICO
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resumen del Sistema",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "• Total de clientes registrados: ${clientes.size}")
                    Text(text = "• Total de pedidos realizados: ${pedidos.size}")
                    Text(text = "• Último cliente registrado: $ultimoCliente")
                }
            }
        }

        // BLOQUE 2: FORMULARIO PARA AGREGAR DATOS EN VIVO
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Registrar Nuevo Cliente y Pedido",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del cliente") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección de entrega") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono (ej. 56912345678)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo (ej. cliente@correo.com)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            viewModel.registrarClienteYPedido(
                                nombre = nombre,
                                direccion = direccion,
                                telefono = telefono,
                                email = email,
                                producto = viewModel.catalogo.first(),
                                cantidad = 2
                            )
                            nombre = ""
                            direccion = ""
                            telefono = ""
                            email = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar Pedido")
                    }
                    if (mensajeEstado.isNotBlank()) {
                        Text(
                            text = mensajeEstado,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        //BLOQUE 3: LISTA DINÁMICA DE PEDIDOS REGISTRADOS
        item {
            Text(
                text = "Pedidos Registrados (${pedidos.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(pedidos, key = { it.idPedido }) { pedido ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Pedido #${pedido.idPedido} — ${pedido.cliente.nombre}",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Correo: ${pedido.cliente.email} | Tel: ${pedido.cliente.contacto}")
                    Text(text = "Pizzas: ${pedido.productos.size} | Total: $${pedido.calcularTotal()} | Estado : ${pedido.estado}")
                }
            }
        }
    }
}