package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.supplierList
import com.example.ucp2.ui.customwidget.topAppBar
import com.example.ucp2.ui.view.supplier.dropdownSupplier
import com.example.ucp2.ui.viewmodel.barang.FormErrorState
import com.example.ucp2.ui.viewmodel.barang.barangEvent
import com.example.ucp2.ui.viewmodel.barang.barangUIState
import com.example.ucp2.ui.viewmodel.barang.barangViewModel
import com.example.ucp2.ui.viewmodel.penyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun insertBarang(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: barangViewModel = viewModel(factory = penyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState // Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                // Tampilkan snackbar
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                judul = "Tambah Barang"
            )

            // Isi Body
            InsertBodyBarang(
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
fun InsertBodyBarang(
    modifier: Modifier = Modifier,
    onValueChange: (barangEvent) -> Unit,
    uiState: barangUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormBarang(
            barangEvent = uiState.barangEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
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
fun FormBarang(
    barangEvent: barangEvent = barangEvent(),
    onValueChange: (barangEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // ID
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.id,
            onValueChange = { onValueChange(barangEvent.copy(id = it)) },
            label = { Text(text = "ID") },
            isError = errorState.id != null,
            placeholder = { Text(text = "Masukkan ID Barang") }
        )
        Text(
            text = errorState.id ?: "",
            color = Color.Red
        )

        // Nama
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.nama,
            onValueChange = { onValueChange(barangEvent.copy(nama = it)) },
            label = { Text(text = "Nama") },
            isError = errorState.nama != null,
            placeholder = { Text(text = "Masukkan Nama Barang") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        // Deskripsi
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = { onValueChange(barangEvent.copy(deskripsi = it)) },
            label = { Text(text = "Deskripsi") },
            isError = errorState.deskripsi != null,
            placeholder = { Text(text = "Masukkan Deskripsi Barang") }
        )
        Text(
            text = errorState.deskripsi ?: "",
            color = Color.Red
        )

        // Harga
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.harga,
            onValueChange = {
                onValueChange(barangEvent.copy(harga = it))
            },
            label = { Text("Harga") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukan Harga") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        if (errorState.harga != null) {
            Text(
                text = errorState.harga ?: "",
                color = Color.Red
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.stok,
            onValueChange = {
                onValueChange(barangEvent.copy(stok = it))
            },
            label = { Text("Stok") },
            isError = errorState.stok != null,
            placeholder = { Text("Masukan Stok") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Text(
            text = errorState.stok ?: "",
            color = Color.Red
        )

        // Dropdown untuk Nama Supplier
        dropdownSupplier(
            selectedValue = barangEvent.namasupplier,
            label = "Pilih Supplier",
            onValueChangedEvent = { pilihanSuplier ->
                onValueChange(barangEvent.copy(namasupplier = pilihanSuplier))
            },
            pilihan = supplierList.ListNamaSuplier()
        )
        Text(
            text = errorState.namasupplier ?: "",
            color = Color.Red
        )
    }
}
