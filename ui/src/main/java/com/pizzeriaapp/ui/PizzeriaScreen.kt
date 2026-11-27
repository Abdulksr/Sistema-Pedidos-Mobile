package com.pizzeriaapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
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
    val pantallaActual by viewModel.pantallaActual.observeAsState(PantallaApp.RESUMEN)
    val cargando by viewModel.cargando.observeAsState(false)
    val mensajeProgreso by viewModel.mensajeProgreso.observeAsState("")

    // Estado local para Fade In inicial y menú desplegable
    var pantallaIniciada by remember { mutableStateOf(false) }
    var menuExpandido by remember { mutableStateOf(false) }

    // Dispara la animación Fade In al cargar la pantalla principal
    LaunchedEffect(Unit) {
        pantallaIniciada = true
    }

    // 2. Estado local del formulario
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cantidadTexto by remember { mutableStateOf("1") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("🍕 ${pantallaActual.titulo}") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    // Botón para probar la recarga con ProgressIndicator
                    IconButton(onClick = { viewModel.recargarResumen() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar resumen")
                    }
                    // Botón del Menú Desplegable
                    Box {
                        IconButton(onClick = { menuExpandido = true }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                        DropdownMenu(
                            expanded = menuExpandido,
                            onDismissRequest = { menuExpandido = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Resumen General") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Home, contentDescription
                                        = null
                                    )
                                },
                                onClick = {
                                    menuExpandido = false
                                    viewModel.navegarA(PantallaApp.RESUMEN)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Gestión de Clientes") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    menuExpandido = false
                                    viewModel.navegarA(PantallaApp.CLIENTES)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Catálogo de Productos") },
                                leadingIcon = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.List, contentDescription
                                        = null
                                    )
                                },
                                onClick = {
                                    menuExpandido = false
                                    viewModel.navegarA(PantallaApp.PRODUCTOS)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Registro y Pedidos") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.ShoppingCart,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    menuExpandido = false
                                    viewModel.navegarA(PantallaApp.PEDIDOS)
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. NOTIFICACIÓN DE PROGRESO
            AnimatedVisibility(
                visible = cargando,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(24.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = mensajeProgreso.ifBlank { "Cargando datos..." },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 2. CONTENIDO PRINCIPAL CON ANIMACIÓN FADE IN / FADE OUT
            AnimatedVisibility(
                visible = pantallaIniciada && !cargando,
                enter = fadeIn(animationSpec = tween(600)),
                exit = fadeOut(animationSpec = tween(350))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    when (pantallaActual) {
                        PantallaApp.RESUMEN -> {
                            item {
                                Text(
                                    text = "Bienvenidos a la mejor pizzería de la ciudad",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor =
                                            MaterialTheme.colorScheme.primaryContainer
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "📊 Resumen del Sistema",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text("• Total de clientes registrados: ${clientes.size}")
                                        Text("• Total de pedidos realizados: ${pedidos.size}")
                                        Text("• Último cliente registrado: $ultimoCliente")
                                    }

                                }
                            }
                            item {
                                Button(
                                    onClick = { viewModel.navegarA(PantallaApp.PEDIDOS) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Ir a Registrar Pedido")
                                }
                            }
                            item {
                                Text(
                                    text = "🧾 Pedidos Registrados (${pedidos.size})",
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
                                        Text("Correo: ${pedido.cliente.email} | Tel: ${pedido.cliente.contacto}")
                                        Text("Pizzas: ${pedido.productos.size} | Total: $${pedido.calcularTotal()} | Estado: ${pedido.estado}")
                                    }
                                }
                            }
                        }

                        PantallaApp.CLIENTES -> {
                            item {
                                Text(
                                    text = "👥 Clientes Registrados (${clientes.size})",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            items(clientes) { cliente ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(cliente.nombre, fontWeight = FontWeight.Bold)
                                        Text("Correo: ${cliente.email}")
                                        Text("Teléfono: ${cliente.contacto}")
                                        Text("Dirección: ${cliente.direccion}")
                                    }
                                }
                            }
                        }

                        PantallaApp.PRODUCTOS -> {
                            item {
                                Text(
                                    text = "🍕 Catálogo de Productos (${viewModel.catalogo.size})",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            items(viewModel.catalogo) { producto ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(producto.nombre, fontWeight = FontWeight.Bold)
                                        Text("Categoría: ${producto.categoria} | Precio: $${producto.precio}")
                                        Text("Ingredientes: ${producto.ingredientes.joinToString(", ")}")
                                    }
                                }
                            }
                        }

                        PantallaApp.PEDIDOS -> {
                            item {
                                Card(modifier = Modifier.fillParentMaxSize()) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = "➕ Registrar Nuevo Cliente y Pedido",
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
                                        OutlinedTextField(
                                            value = cantidadTexto,
                                            onValueChange = { cantidadTexto = it },
                                            label = { Text("Cantidad de pizzas (1-100)") },
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (mensajeEstado.isNotBlank()) {
                                            Text(
                                                text = mensajeEstado,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = if (mensajeEstado.startsWith("Error")) {
                                                    MaterialTheme.colorScheme.error
                                                } else {
                                                    MaterialTheme.colorScheme.primary
                                                }
                                            )
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(
                                            onClick = {
                                                viewModel.registrarClienteYPedido(
                                                    nombre = nombre,
                                                    direccion = direccion,
                                                    telefono = telefono,
                                                    email = email,
                                                    producto = viewModel.catalogo.first(),
                                                    cantidad = cantidadTexto.toIntOrNull() ?: 0
                                                )
                                                nombre = ""; direccion = ""; telefono = "";
                                                email = ""; cantidadTexto = "1"
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(52.dp)
                                        ) {
                                            Text("Guardar Pedido")
                                        }


                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}