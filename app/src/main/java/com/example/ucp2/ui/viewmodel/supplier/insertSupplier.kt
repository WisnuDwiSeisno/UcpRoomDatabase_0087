package com.example.ucp2.ui.viewmodel.supplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.repository.repoSupp
import kotlinx.coroutines.launch


class insertSupplier(private val repoSupp: repoSupp) : ViewModel() {

    var uiState by mutableStateOf(SuppUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(supplierEvent: supplierEvent) {
        uiState = uiState.copy(
            supplierEvent = supplierEvent,
        )
    }

    // Validasi data import pengguna
    private fun validateFields(): Boolean {
        val event = uiState.supplierEvent
        val errorState = FormErrorState(
            id = if (event.id.isNotEmpty()) null else "id tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            kontak = if (event.kontak.isNotEmpty()) null else "Kontak tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.supplierEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repoSupp.insertSupp(currentEvent.toSupplierEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        supplierEvent = supplierEvent(), //Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda."
            )
        }
    }

    //Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}


data class SuppUIState(
    val supplierEvent: supplierEvent = supplierEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val id: String? = null,
    val nama: String? = null,
    val kontak: String? = null,
    val alamat: String? = null
) {
    fun isValid(): Boolean {
        return id == null && nama == null && kontak == null &&
                alamat == null
    }
}

//  menyimpan input form ke dalam entity
fun supplierEvent.toSupplierEntity(): Supplier = Supplier(
    id = id,
    nama = nama,
    kontak = kontak,
    alamat = alamat
)

// data class Variabel yang menyimpan data input form
data class supplierEvent(
    val id: String = "",
    val nama: String = "",
    val kontak: String = "",
    val alamat: String = "",
)