package com.manish.app.clearquotetask.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.manish.app.clearquotetask.viewmodel.MainViewModel

@Composable
fun ImageAnalysisAndObjectDetector(mainViewModel: MainViewModel,
                                   navController: NavController) {
    val selectedImageUri = mainViewModel.getSelectedImageUri().collectAsState(Uri.parse(""))
    val outputBitmap by mainViewModel.getOutputBitmap().collectAsState(null)

    Log.d("ImageAnalysisAndObjectDetector", "selectedImageUri ${selectedImageUri.value}")
    Log.d("ImageAnalysisAndObjectDetector", "outputBitmap $outputBitmap")

    Column (modifier = Modifier
        .padding(top = 10.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly) {

        Text("Original Image", fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp))

        Image(painter = rememberAsyncImagePainter(selectedImageUri.value),
            contentDescription = "selected_image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(start = 8.dp, end = 8.dp),
            contentScale = ContentScale.FillBounds
        )

        Text("Object detection image", fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp))

        Image(painter = rememberAsyncImagePainter(outputBitmap),
            contentDescription = "selected_image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(start = 8.dp, end = 8.dp),
            contentScale = ContentScale.FillBounds)


        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
            onClick = { navController.popBackStack() }) {
            Text(text = "Go to main")
        }
    }

}