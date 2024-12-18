package com.example.ucp2.repository

import com.example.ucp2.data.dao.supplierDao
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

class localRepoSupp (
    private val supplierDao: supplierDao
) : repoSupp {
    override suspend fun insertSupp(supplier: Supplier) {
        supplierDao.insertSupp(supplier)
    }
    override fun getAllSupp(): Flow<List<Supplier>> =
        supplierDao.getAllSupp()
}