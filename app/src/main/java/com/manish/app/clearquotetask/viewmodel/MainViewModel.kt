package com.manish.app.clearquotetask.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manish.app.clearquotetask.helper.DrawDetectionResultHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _selectedImageUri = MutableStateFlow<Uri>(Uri.parse(""))
    private var _outputImage = MutableStateFlow<Bitmap?>(null)

    fun setSelectedImageUri(uri:Uri) {
        viewModelScope.launch {
            _selectedImageUri.emit(uri)
        }
    }

    fun getSelectedImageUri() : Flow<Uri> {
        return _selectedImageUri
    }

    fun getOutputBitmap(): Flow<Bitmap?> {
        Log.d("MainViewModel", "getOutputBitmap: called")
        viewModelScope.launch {
            DrawDetectionResultHelper.getOutputBitmap().collectLatest {
                _outputImage.emit(it)
            }
        }

        return _outputImage
    }

}