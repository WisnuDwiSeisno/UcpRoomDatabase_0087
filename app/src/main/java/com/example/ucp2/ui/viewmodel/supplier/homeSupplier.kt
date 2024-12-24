package com.example.ucp2.ui.viewmodel.supplier

import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.repository.repoSupp

class homeSupplier(
    private val repoSupp: repoSupp
) : ViewModel() {
}

data class HomeUiState(
    val listSupp: List<Supplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)