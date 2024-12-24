package com.example.ucp2.ui.view.supplier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.topAppBar
import com.example.ucp2.ui.viewmodel.penyediaViewModel
import com.example.ucp2.ui.viewmodel.supplier.*
import kotlinx.coroutines.launch

@Composable
fun insertSupplier(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: insertSupplier = viewModel(factory = penyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            topAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Supplier"
            )

            // Isi Body
            InsertSupplierBody(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    // Update state di ViewModel
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertSupplierBody(
    modifier: Modifier = Modifier,
    onValueChange: (supplierEvent) -> Unit,
    uiState: SuppUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormSupplier(
            supplierEvent = uiState.supplierEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormSupplier(
    supplierEvent: supplierEvent = supplierEvent(),
    onValueChange: (supplierEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // ID
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.id,
            onValueChange = { onValueChange(supplierEvent.copy(id = it)) },
            label = { Text(text = "ID Supplier") },
            isError = errorState.id != null,
            placeholder = { Text(text = "Masukkan ID Supplier") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { /* Focus on next field */ }
            )
        )
        Text(
            text = errorState.id ?: "",
            color = Color.Red
        )

        // Nama
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.nama,
            onValueChange = { onValueChange(supplierEvent.copy(nama = it)) },
            label = { Text(text = "Nama Supplier") },
            isError = errorState.nama != null,
            placeholder = { Text(text = "Masukkan Nama Supplier") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { /* Focus on next field */ }
            )
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        // Kontak
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.kontak,
            onValueChange = { onValueChange(supplierEvent.copy(kontak = it)) },
            label = { Text(text = "Kontak Supplier") },
            isError = errorState.kontak != null,
            placeholder = { Text(text = "Masukkan Kontak Supplier") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onNext = { }
            )
        )
        Text(
            text = errorState.kontak ?: "",
            color = Color.Red
        )

        // Alamat
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.alamat,
            onValueChange = { onValueChange(supplierEvent.copy(alamat = it)) },
            label = { Text(text = "Alamat Supplier") },
            isError = errorState.alamat != null,
            placeholder = { Text(text = "Masukkan Alamat Supplier") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle Done action */ }
            )
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )
    }
}
