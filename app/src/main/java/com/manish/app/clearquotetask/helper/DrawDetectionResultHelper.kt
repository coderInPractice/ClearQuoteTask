package com.manish.app.clearquotetask.helper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.manish.app.clearquotetask.modals.DetectedObjectsModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


object DrawDetectionResultHelper {

    private val _outputBitmap = MutableStateFlow<Bitmap?>(null)

    fun drawDetectionResultsOnImage(image: InputImage,
                                    detectedObjectsModalList:List<DetectedObjectsModal>) {
        Log.d("DrawDetectionResultHelper", "drawDetectionResultsOnImage: outside coroutine")
        CoroutineScope(Dispatchers.Default).launch {
            Log.d("DrawDetectionResultHelper", "drawDetectionResultsOnImage: inside coroutine")
            val tempOutputBitmap = image.bitmapInternal?.copy(Bitmap.Config.ARGB_8888, true)
            val pen = Paint()
            pen.textAlign = Paint.Align.LEFT
            val canvas = Canvas(tempOutputBitmap!!)

            for (detectedObjectModal in detectedObjectsModalList) {
                // draw bounding box
                pen.setColor(Color.RED)
                pen.strokeWidth = 8F
                pen.style = Paint.Style.STROKE
                canvas.drawRect(detectedObjectModal.rect, pen)

                val tagSize = Rect(0, 0, 0, 0)


                // calculate the right font size
                pen.style = Paint.Style.FILL_AND_STROKE
                pen.color = Color.YELLOW
                pen.strokeWidth = 2f
                pen.textSize = 96f

                pen.getTextBounds(detectedObjectModal.labelsWithConfidence, 0,
                    detectedObjectModal.labelsWithConfidence!!.length, tagSize)
                val fontSize: Float = pen.textSize * detectedObjectModal.rect.width() / tagSize.width()

                // adjust the font size so texts are inside the bounding box
                if (fontSize < pen.textSize) {
                    pen.textSize = fontSize
                }

                var margin: Float = (detectedObjectModal.rect.width() - tagSize.width()) / 2.0f
                if (margin < 0f) margin = 0f
                canvas.drawText(
                    detectedObjectModal.labelsWithConfidence, detectedObjectModal.rect.left + margin,
                    (detectedObjectModal.rect.top + tagSize.height()).toFloat(), pen
                )
            }

            _outputBitmap.emit(tempOutputBitmap)

        }

    }

    fun getOutputBitmap() : Flow<Bitmap?>{
        Log.d("DrawDetectionResultHelper", "getOutputBitmap: ")
        return _outputBitmap
    }
}