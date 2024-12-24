package com.example.ucp2.ui.viewmodel.supplier

import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.repository.repoSupp

class insertSupplier(private val repoSupp: repoSupp) : ViewModel() {
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