package com.example.ucp2.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.repoBarang
import kotlinx.coroutines.launch

class barangViewModel(private val repoBarang: repoBarang) : ViewModel() {


    var uiState by mutableStateOf(barangUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(barangEvent: barangEvent){
        uiState = uiState.copy(
            barangEvent = barangEvent,
        )
    }
    // Validasi data import pengguna
    private fun validateFields(): Boolean{
        val  event = uiState.barangEvent
        val errorState = FormErrorState(
            id = if (event.id.isNotEmpty())  null else "ID tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            harga = if (event.harga == 0.0) null else "Harga tidak boleh kosong",
            stok = if (event.stok > 0) null else "Stok tidak boleh kosong",
            namasupplier = if (event.namasupplier.isNotEmpty()) null else "Nama Supplier tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    //Menyimpan data ke repository
    fun saveData(){
        val currentEvent = uiState.barangEvent

        if(validateFields()){
            viewModelScope.launch {
                try{
                    repoBarang.insertBarang(currentEvent.toBarangEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        barangEvent = barangEvent(), //Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda."
            )
        }
    }
}

data class barangUIState(
    val barangEvent: barangEvent = barangEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val id: String? = null,
    val nama: String? = null,
    val deskripsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namasupplier: String? = null,
) {
    fun isValid(): Boolean {
        return id == null && nama == null && deskripsi == null &&
                harga == null && stok == null && namasupplier == null
    }
}

//  menyimpan input form ke dalam entity
fun barangEvent.toBarangEntity(): Barang = Barang(
    id = id,
    nama = nama,
    deskripsi = deskripsi,
    harga = harga,
    stok = stok,
    namasupplier = namasupplier
)

// data class Variabel yang menyimpan data input form
data class barangEvent(
    val id: String = "",
    val nama: String = "",
    val deskripsi: String = "",
    val harga: Double = 0.0,
    val stok: Int = 0,
    val namasupplier: String = "",
)