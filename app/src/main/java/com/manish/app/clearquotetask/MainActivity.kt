package com.manish.app.clearquotetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manish.app.clearquotetask.screens.ImageAnalysisAndObjectDetector
import com.manish.app.clearquotetask.screens.OpenGallery
import com.manish.app.clearquotetask.ui.theme.ClearQuoteTaskTheme
import com.manish.app.clearquotetask.viewmodel.MainViewModel

object NavDestinations {
    const val OPEN_GALLERY = "open_gallery"
    const val IMAGE_ANALYZER = "image_analyzer"
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClearQuoteTaskTheme {
                val mainViewModel = viewModel<MainViewModel>()
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   NavHost(navController = navController,
                       startDestination = NavDestinations.OPEN_GALLERY
                   ) {
                       composable(route = NavDestinations.OPEN_GALLERY) {
                           OpenGallery(Modifier.padding(innerPadding), mainViewModel, navController)
                       }
                       composable(route = NavDestinations.IMAGE_ANALYZER) {
                           ImageAnalysisAndObjectDetector(mainViewModel, navController)
                       }
                   }
                }
            }
        }

    }
}



