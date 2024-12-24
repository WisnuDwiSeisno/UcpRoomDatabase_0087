package com.example.ucp2.ui.navigation

interface alamatNavigasi {
    val route: String
}

object DestinasiHome : alamatNavigasi {
    override val route = "home"
}

object DestinasiListBarang : alamatNavigasi {
    override val route = "listBarang"
}

object DestinasiListSupplier : alamatNavigasi {
    override val route = "listSupplier"
}

object DestinasiDetail : alamatNavigasi {
    override val route = "detail"
    const val id = "id"
    val routesWithArg = "$route/{$id}"
}

object DestinasiUpdate : alamatNavigasi {
    override val route = "update"
    const val id = "id"
    val routesWithArg = "${route}/{${id}}"
}

object DestinasiInsert : alamatNavigasi {
    override val route: String = "Insert Barang"
}

object DestinasiInsertSupp : alamatNavigasi {
    override val route: String = "Insert Supplier"
}