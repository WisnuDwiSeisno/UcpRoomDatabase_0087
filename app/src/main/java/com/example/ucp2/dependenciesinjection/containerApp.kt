package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.tokoDatabase
import com.example.ucp2.repository.localRepoBarang
import com.example.ucp2.repository.localRepoSupp
import com.example.ucp2.repository.repoBarang
import com.example.ucp2.repository.repoSupp

interface InterfaceContainerApp {
    val repoBarang: repoBarang
    val repoSupp: repoSupp
}

class containerApp(private val context: Context) : InterfaceContainerApp {
    override val repoBarang: repoBarang by lazy {
        localRepoBarang(tokoDatabase.getDatabase(context).barangDao())
    }
    override val repoSupp: repoSupp by lazy {
        localRepoSupp(tokoDatabase.getDatabase(context).supplierDao())
    }
}