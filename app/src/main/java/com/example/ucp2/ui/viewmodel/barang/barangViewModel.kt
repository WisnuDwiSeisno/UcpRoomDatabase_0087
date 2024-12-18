package com.example.ucp2.ui.viewmodel.barang

import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.repoBarang

class barangViewModel(private val repoBarang: repoBarang) : ViewModel() {

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
    val harga: String = "",
    val stok: String = "",
    val namasupplier: String = "",
)