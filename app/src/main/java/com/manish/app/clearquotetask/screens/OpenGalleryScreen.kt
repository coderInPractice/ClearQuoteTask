package com.manish.app.clearquotetask.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.manish.app.clearquotetask.NavDestinations
import com.manish.app.clearquotetask.helper.ObjectDetectHelper
import com.manish.app.clearquotetask.viewmodel.MainViewModel
import java.io.IOException

@Composable
fun OpenGallery(modifier: Modifier, mainViewModel:MainViewModel, navController: NavController) {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            mainViewModel.setSelectedImageUri(uri)
            navController.navigate(NavDestinations.IMAGE_ANALYZER)
            val image: InputImage
            try {
                image = InputImage.fromFilePath(context, uri)
                ObjectDetectHelper.detectObjects (image)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
            Toast.makeText(context, "No Media Selected", Toast.LENGTH_SHORT).show()
        }
    }
    Box(modifier = modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Button(shape = RoundedCornerShape(10.dp),
            onClick = { pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts
                .PickVisualMedia.ImageOnly)
            )} ) {
            Text(text = "Open photos")
        }
    }

}