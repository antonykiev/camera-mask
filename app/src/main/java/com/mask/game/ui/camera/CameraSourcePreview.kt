package com.mask.game.ui.camera

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.google.android.gms.common.images.Size
import com.google.android.gms.vision.CameraSource
import java.io.IOException


class CameraSourcePreview(context: Context, attrs: AttributeSet?): ViewGroup(context, attrs) {

    private val TAG = "CameraSourcePreview"

    private var mSurfaceView: SurfaceView? = null
    private var mStartRequested = false
    private var mSurfaceAvailable = false
    private var mCameraSource: CameraSource? = null

    private var mOverlay: GraphicOverlay? = null

    init {
        mStartRequested = false
        mSurfaceAvailable = false
        mSurfaceView = SurfaceView(context)
        mSurfaceView!!.holder.addCallback(SurfaceCallback())
        addView(mSurfaceView)
    }

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource?) {
        cameraSource ?: stop()

        mCameraSource = cameraSource

        mCameraSource?.let {
            mStartRequested = true
            startIfReady()
        }
    }

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource?, overlay: GraphicOverlay?) {
        mOverlay = overlay
        start(cameraSource)
    }

    fun stop() {
        mCameraSource?.let {
            it.stop()
        }
    }

    fun getBitmapFromCameraContent(onPictureTake: (Bitmap) -> Unit) {

        mCameraSource?.takePicture({}, {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            onPictureTake(bitmap)
        })
    }

    fun release() {
        mCameraSource?.let {
            it.release()
            mCameraSource = null
        }
    }

    @Throws(IOException::class)
    private fun startIfReady() {
        if (mStartRequested && mSurfaceAvailable) {
            mCameraSource!!.start(mSurfaceView!!.holder)
            if (mOverlay != null) {
                val size: Size = mCameraSource!!.previewSize
                val min: Int = Math.min(size.getWidth(), size.getHeight())
                val max: Int = Math.max(size.getWidth(), size.getHeight())
                if (isPortraitMode()) {
                    // Swap width and height sizes when in portrait, since it will be rotated by
                    // 90 degrees
                    mOverlay!!.setCameraInfo(min, max, mCameraSource!!.cameraFacing)
                } else {
                    mOverlay!!.setCameraInfo(max, min, mCameraSource!!.cameraFacing)
                }
                mOverlay!!.clear()
            }
            mStartRequested = false
        }
    }

    private inner class SurfaceCallback : SurfaceHolder.Callback {
        override fun surfaceCreated(surface: SurfaceHolder) {
            mSurfaceAvailable = true
            try {
                startIfReady()
            } catch (e: IOException) {
                Log.e(TAG, "Could not start camera source.", e)
            }
        }

        override fun surfaceDestroyed(surface: SurfaceHolder) {
            mSurfaceAvailable = false
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var width = 320
        var height = 240
        if (mCameraSource != null) {
            val size: Size? = mCameraSource!!.previewSize
            if (size != null) {
                width = size.width
                height = size.height
            }
        }

        // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
        if (isPortraitMode()) {
            val tmp = width
            width = height
            height = tmp
        }
        val layoutWidth = right - left
        val layoutHeight = bottom - top

        // Computes height and width for potentially doing fit width.
        var childWidth = layoutWidth
        var childHeight = (layoutWidth.toFloat() / width.toFloat() * height).toInt()

        // If height is too tall using fit width, does fit height instead.
        if (childHeight > layoutHeight) {
            childHeight = layoutHeight
            childWidth = (layoutHeight.toFloat() / height.toFloat() * width) as Int
        }
        for (i in 0 until childCount) {
            getChildAt(i).layout(0, 0, childWidth, childHeight)
        }
        try {
            startIfReady()
        } catch (e: IOException) {
            Log.e(TAG, "Could not start camera source.", e)
        }
    }

    private fun isPortraitMode(): Boolean {
        val orientation: Int = context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true
        }
        Log.d(TAG, "isPortraitMode returning false by default")
        return false
    }
}