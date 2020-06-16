package com.financia.qrreadertest

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage.fromMediaImage


class ImageAnalyzer(private val mainActivity: MainActivity) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            mainActivity.scanBarcodes(image, imageProxy)
            //imageProxy.close()
            // Pass image to an ML Kit Vision API
            // ...
        } else {
            imageProxy.close()
        }
    }
}