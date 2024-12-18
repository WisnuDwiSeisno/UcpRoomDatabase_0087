package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.barangDao
import com.example.ucp2.data.dao.supplierDao
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.data.entity.Supplier

// Mendefinisikan database dengan tabel Mahasiswa
@Database(entities = [Barang::class, Supplier::class /* masukin disini untuk manggil entitas */], version = 1, exportSchema = false)
abstract class tokoDatabase : RoomDatabase() {
    // Mendefinisikan fungsi untuk mengakses data Mahasiswa
    abstract fun barangDao(): barangDao
    abstract fun supplierDao(): supplierDao


    companion object {
        @Volatile // Memastikan bahwa nilai variabel Instance selalu sama di semua Thread
        private var Instance: tokoDatabase? = null

        fun getDatabase(context: Context): tokoDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    tokoDatabase::class.java, // Class Database
                    "tokoDatabase" //Nama Database
                )
                    .build().also { Instance = it }
            })
        }
    }
}