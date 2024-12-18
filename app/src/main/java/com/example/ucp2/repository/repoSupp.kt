package com.example.ucp2.repository

import com.example.ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

interface repoSupp {
    suspend fun insertSupp(supplier: Supplier)
    fun getAllSupp(): Flow<List<Supplier>>
}