package com.example.ucp2.ui.viewmodel.barang


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.repoBarang
import com.example.ucp2.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class detailBarangVM(
    savedStateHandle: SavedStateHandle,
    private val repoBarang: repoBarang,
) : ViewModel() {
    private val _id: String = checkNotNull(savedStateHandle[DestinasiDetail.id])

    val detailUiState: StateFlow<DetailUiState> = repoBarang.getBarang(_id)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )
}

data class DetailUiState(
    val detailUiEvent: barangEvent = barangEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == barangEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != barangEvent()
}

fun Barang.toDetailUiEvent(): barangEvent {
    return barangEvent(
        id = id,
        nama = nama,
        deskripsi = deskripsi,
        harga = harga.toString(),
        stok = stok.toString(),
        namasupplier = namasupplier
    )
}