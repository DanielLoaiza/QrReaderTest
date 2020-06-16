package com.financia.qrreadertest

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.Log
import android.view.TextureView
import java.io.IOException

class CameraModule : TextureView.SurfaceTextureListener {
    var mCamera: Camera? = null
    lateinit var activity: MainActivity
    var orientation : Int? = null
    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mCamera = camera
        try {
            mCamera!!.setPreviewTexture(surface)
            val params = mCamera!!.getParameters()
            params.pictureFormat = ImageFormat.NV21
            params.setRotation(90)
            mCamera!!.parameters = params
            mCamera!!.setDisplayOrientation(90)
            mCamera!!.setPreviewCallback{data, camera ->
              //activity.scanBarcodes(data)
            }
            mCamera!!.startPreview()
        } catch (ioe: IOException) {
            Log.e("ERROR",ioe.toString())
            //Snaplog.d(ioe.toString())
        }
    }
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        // Ignored, Camera does all the work for us
    }
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mCamera!!.stopPreview()
        mCamera!!.release()
        return true
    }
    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    }
    private val camera: Camera?
        get() {
            var cam: Camera? = null
            try {
                cam = Camera.open()
            } catch (e: RuntimeException) {
                // error
                Log.e("ERROR",e.toString())
            }
            return cam
        }
}