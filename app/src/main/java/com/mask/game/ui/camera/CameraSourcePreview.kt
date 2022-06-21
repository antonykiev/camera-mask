package com.mask.game.ui.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.google.android.gms.common.images.Size
import com.google.android.gms.vision.CameraSource
import java.io.IOException


class CameraSourcePreview(context: Context, attrs: AttributeSet?): ViewGroup(context, attrs) {

    private val TAG = "CameraSourcePreview"

    private var surfaceView: SurfaceView? = null
    private var startRequested = false
    private var surfaceAvailable = false
    private var cameraSource: CameraSource? = null

    private var overlay: GraphicOverlay? = null

    init {
        startRequested = false
        surfaceAvailable = false
        surfaceView = SurfaceView(context)
        surfaceView?.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        surfaceView!!.holder.addCallback(SurfaceCallback())
        addView(surfaceView)
    }

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource?) {
        cameraSource ?: stop()

        this.cameraSource = cameraSource

        this.cameraSource?.let {
            startRequested = true
            startIfReady()
        }
    }

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource?, overlay: GraphicOverlay?) {
        this.overlay = overlay
        start(cameraSource)
    }

    fun stop() {
        cameraSource?.let {
            it.stop()
        }
    }

    fun getBitmapFromCameraContent(onPictureTake: (Bitmap) -> Unit) {

        cameraSource?.takePicture({}, {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            onPictureTake(bitmap)
        })
    }

    fun release() {
        cameraSource?.let {
            it.release()
            cameraSource = null
        }
    }

    @Throws(IOException::class)
    private fun startIfReady() {
        if (startRequested && surfaceAvailable) {
            cameraSource!!.start(surfaceView!!.holder)
            if (overlay != null) {
                val size: Size = cameraSource!!.previewSize
//                val min: Int = Math.min(size.width, size.height)
//                val max: Int = Math.max(size.getWidth(), size.getHeight())

                overlay!!.setCameraInfo(size.height, size.width, cameraSource!!.cameraFacing)

//                if (isPortraitMode()) {
//                    // Swap width and height sizes when in portrait, since it will be rotated by
//                    // 90 degrees
//                    overlay!!.setCameraInfo(min, max, cameraSource!!.cameraFacing)
//                } else {
//                    overlay!!.setCameraInfo(max, min, cameraSource!!.cameraFacing)
//                }
                overlay!!.clear()
            }
            startRequested = false
        }
    }

    private inner class SurfaceCallback : SurfaceHolder.Callback {
        override fun surfaceCreated(surface: SurfaceHolder) {
            surfaceAvailable = true
            try {
                startIfReady()
            } catch (e: IOException) {
                Log.e(TAG, "Could not start camera source.", e)
            }
        }

        override fun surfaceDestroyed(surface: SurfaceHolder) {
            surfaceAvailable = false
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var previewWidth = 320
        var previewHeight = 240
        if (cameraSource != null) {
            val size: Size? = cameraSource!!.previewSize
            if (size != null) {
                previewWidth = size.width
                previewHeight = size.height
            }
        }

        val dm = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(dm)
        val displayWidth = dm.widthPixels
        val displayHeight = dm.heightPixels
        val diff: Int = (width / (displayHeight / displayWidth)) / 2

        for (i in 0 until childCount) {
            getChildAt(i).layout(-diff, 0, width + diff, height)
        }
        try {
            startIfReady()
        } catch (e: IOException) {
            Log.e(TAG, "Could not start camera source.", e)
        }
    }

//    private fun isPortraitMode(): Boolean {
//        val orientation: Int = context.resources.configuration.orientation
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            return false
//        }
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            return true
//        }
//        Log.d(TAG, "isPortraitMode returning false by default")
//        return false
//    }
}