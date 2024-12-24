package com.example.ucp2.ui.viewmodel.barang


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.repoBarang
import com.example.ucp2.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class updateBarang(
    savedStateHandle: SavedStateHandle,
    private val repoBarang: repoBarang
) : ViewModel() {

    var updateUIState by mutableStateOf(barangUIState())
        private set

    private val _id: String = checkNotNull(savedStateHandle[DestinasiUpdate.id])

    init {
        viewModelScope.launch {
            updateUIState = repoBarang.getBarang(_id)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(barangEvent: barangEvent) {
        updateUIState = updateUIState.copy(
            barangEvent = barangEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.barangEvent
        val errorState = FormErrorState(
            id = if (event.id.isNotEmpty())  null else "ID tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga tidak boleh kosong",
            stok = if (event.stok.isNotEmpty()) null else "Stok tidak boleh kosong",
            namasupplier = if (event.namasupplier.isNotEmpty()) null else "Nama Supplier tidak boleh kosong"
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.barangEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repoBarang.updateBarang(currentEvent.toBarangEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data Gagal Diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data Gagal diupdate"
            )
        }
    }


    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Barang.toUIStateMhs(): barangUIState = barangUIState(
    barangEvent = this.toDetailUiEvent(),
)