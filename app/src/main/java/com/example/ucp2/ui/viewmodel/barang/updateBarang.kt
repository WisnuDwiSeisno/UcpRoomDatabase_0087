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
}

fun Barang.toUIStateMhs(): barangUIState = barangUIState(
    barangEvent = this.toDetailUiEvent(),
)