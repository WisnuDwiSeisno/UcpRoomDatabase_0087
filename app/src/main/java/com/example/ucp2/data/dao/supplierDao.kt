package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

@Dao
interface supplierDao {
    @Insert
    suspend fun insertSupp(
        supplier: Supplier
    )

    @Query("SELECT * FROM supplier ORDER BY nama ASC")
    fun getAllSupp(): Flow<List<Supplier>>
}

