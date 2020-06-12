package com.financia.qrreadertest

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import android.util.SparseIntArray
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata

class ImageAnalyzer(private val mainActivity: MainActivity) : ImageAnalysis.Analyzer {

    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val imageRotation = degreesToFirebaseRotation(imageProxy.imageInfo.rotationDegrees)
            val image = FirebaseVisionImage.fromMediaImage(imageProxy.image as Image, imageRotation)
            mainActivity.scanBarcodes(image)
            imageProxy.close()
            // Pass image to an ML Kit Vision API
            // ...
        } else {
            imageProxy.close()
        }
    }
}