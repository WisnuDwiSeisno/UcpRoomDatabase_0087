package com.example.ucp2.repository
import com.example.ucp2.data.dao.barangDao
import com.example.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

class localRepoBarang(
    private val barangDao: barangDao
) : repoBarang {
    override suspend fun insertBarang(barang: Barang) {
        barangDao.insertBarang(barang)
    }
    override fun getAllBarang(): Flow<List<Barang>> =
        barangDao.getAllBarang()
    override fun getBarang(id: String): Flow<Barang> =
        barangDao.getBarang(id)
    override suspend fun deleteBarang(barang: Barang) {
        barangDao.deleteBarang(barang)
    }
    override suspend fun updateBarang(barang: Barang) {
        barangDao.updateBarang(barang)
    }
}