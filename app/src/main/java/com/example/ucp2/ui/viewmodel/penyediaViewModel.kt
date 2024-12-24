package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.TokoApp
import com.example.ucp2.ui.viewmodel.barang.*
import com.example.ucp2.ui.viewmodel.supplier.*

object penyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            barangViewModel(
                TokoApp().containerApp.repoBarang
            )
        }
        initializer {
            homeBarang(
                TokoApp().containerApp.repoBarang
            )
        }
        initializer {
            detailBarangVM(
                createSavedStateHandle(),
                TokoApp().containerApp.repoBarang
            )
        }
        initializer {
            updateBarang(
                createSavedStateHandle(),
                TokoApp().containerApp.repoBarang
            )
        }
        initializer {
            insertSupplier(
                TokoApp().containerApp.repoSupp
            )
        }
        initializer {
            homeSupplier(
                TokoApp().containerApp.repoSupp
            )
        }
    }
}

fun CreationExtras.TokoApp(): TokoApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TokoApp)
