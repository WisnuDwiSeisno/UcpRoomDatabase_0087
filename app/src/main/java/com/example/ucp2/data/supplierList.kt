package com.example.ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewmodel.penyediaViewModel
import com.example.ucp2.ui.viewmodel.supplier.homeSupplier

object supplierList {
    @Composable
    fun ListNamaSuplier(
        listSpr: homeSupplier = viewModel(factory = penyediaViewModel.Factory)
    ) : List<String> {
        val listNamaSupplier by listSpr.homeUiState.collectAsState()
        val NamaSpr = listNamaSupplier.listSupp.map { it.nama }
        return NamaSpr
    }
}
