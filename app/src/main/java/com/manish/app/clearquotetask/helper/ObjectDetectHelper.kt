package com.manish.app.clearquotetask.helper

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.manish.app.clearquotetask.modals.DetectedObjectsModal

object ObjectDetectHelper {
    private val options = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
        .enableMultipleObjects()
        .enableClassification()
        .build()

    fun detectObjects(image: InputImage) {
        val objectDetector = ObjectDetection.getClient(options)
        objectDetector.process(image)
            .addOnSuccessListener { detectObjects ->
                var label = ""
                val detectedObjectsModalList = mutableListOf<DetectedObjectsModal>()
                for(detectedObject in detectObjects) {
                    for (detectedLabel in detectedObject.labels) {
                        val confidenceInPercent = convertFloatToPercentString(detectedLabel.confidence)
                        label = buildString {
                            append(detectedLabel.text)
                            append(":")
                            append(confidenceInPercent)
                            appendLine()
                        }
                    }
                    if (detectedObject.labels.isNotEmpty()) {
                        detectedObjectsModalList.add(
                            DetectedObjectsModal(
                                label,
                                detectedObject.boundingBox
                            )
                        )
                    } else {
                        detectedObjectsModalList.add(DetectedObjectsModal("Unknown",
                            detectedObject.boundingBox
                        ))
                    }

                }
                DrawDetectionResultHelper.drawDetectionResultsOnImage(image,
                    detectedObjectsModalList)
        }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }

    }

    private fun convertFloatToPercentString(confidenceInFloat:Float): String {
        return String.format("%.2f", confidenceInFloat * 100)
    }
}
