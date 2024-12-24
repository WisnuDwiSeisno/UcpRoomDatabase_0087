package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.barang.detailBarang
import com.example.ucp2.ui.view.barang.insertBarang
import com.example.ucp2.ui.view.barang.listBarang
import com.example.ucp2.ui.view.barang.updateBarang
import com.example.ucp2.ui.view.homePage
import com.example.ucp2.ui.view.supplier.insertSupplier
import com.example.ucp2.ui.view.supplier.listSupplier
import com.example.ucp2.ui.viewmodel.barang.detailBarangVM

@Composable
fun pengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(route = DestinasiHome.route) {
            homePage(
                toBarang = { navController.navigate(DestinasiListBarang.route) },
                toSupplier = { navController.navigate(DestinasiListSupplier.route) },
                onBack = { navController.popBackStack() }
            )
        }


        composable(route = DestinasiListSupplier.route) {
            listSupplier(
                onAddSupplier = { navController.navigate(DestinasiInsertSupp.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = DestinasiListBarang.route) {
            listBarang(
                onAddBrg = { navController.navigate(DestinasiInsert.route) },
                onDetailClick = { id -> navController.navigate("${DestinasiDetail.route}/$id") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = DestinasiInsert.route) {
            insertBarang(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertSupp.route) {
            insertSupplier(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetail.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetail.id) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiDetail.id)
            if (id != null) {
                detailBarang(
                    onBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdate.route}/$id") },
                    onDeleteClick = { navController.popBackStack() },
                    modifier = modifier
                )
            } else {
                // Handle case where ID is null (optional)
            }
        }

        composable(
            route = DestinasiUpdate.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdate.id) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiUpdate.id)
            if (id != null) {
                updateBarang(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() },
                    modifier = modifier
                )
            } else {
                // Handle case where ID is null (optional)
            }
        }
    }
}
