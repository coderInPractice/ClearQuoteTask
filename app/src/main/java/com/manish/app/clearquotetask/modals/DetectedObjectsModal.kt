package com.manish.app.clearquotetask.modals

import android.graphics.Rect

data class DetectedObjectsModal(
    val labelsWithConfidence: String? = null,
    val rect: Rect
)
