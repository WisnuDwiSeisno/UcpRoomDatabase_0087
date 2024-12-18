package com.example.ucp2.repository
import com.example.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

interface repoBarang {
    suspend fun insertBarang(barang: Barang)
    fun getAllBarang(): Flow<List<Barang>>
    fun getBarang(id: String): Flow<Barang>
    suspend fun deleteBarang(barang: Barang)
    suspend fun updateBarang(barang: Barang)
}