package com.example.ucp2
import android.app.Application
import com.example.ucp2.dependenciesinjection.containerApp

class TokoApp : Application() {
    // Fungsinya untuk menyimpan instance ContainerApp
    lateinit var containerApp: containerApp

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = containerApp(this)
        // instance adalah object yang dibuat dari class
    }
}