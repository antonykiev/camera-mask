package com.mask.game.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.mask.game.utils.BitmapMerger.normalizeBitmap


object BitmapMerger {

    operator fun invoke(fromPhoto: Bitmap, fromMask: Bitmap): Bitmap {
        val bmOverlay = Bitmap.createBitmap(fromPhoto.width, fromPhoto.height, fromPhoto.config)
        val scaledPhotoBitmap = if (fromPhoto.width > fromMask.width) {
            Bitmap.createScaledBitmap(fromMask, fromPhoto.width, fromPhoto.height, true)
        } else {
            fromMask
        }

        val cx = bmOverlay.width / 2f
        val cy = bmOverlay.height / 2f
        val flippedBitmap = fromPhoto.flip(-1f, 1f, cx, cy)

        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(flippedBitmap, Matrix(), null)
        canvas.drawBitmap(scaledPhotoBitmap, 0f, 0f, null)
        fromPhoto.recycle()
        scaledPhotoBitmap.recycle()
        return bmOverlay
    }

    private fun Bitmap.flip(x: Float, y: Float, cx: Float, cy: Float): Bitmap {
        val matrix = Matrix().apply {
            postScale(x, y, cx, cy)

            if (this@flip.height <= this@flip.width){
                postRotate(90F)
            }
        }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    private fun Bitmap.normalizeBitmap(): Bitmap {

        if (this.height > this.width)
            return this

        val matrix = Matrix()

        matrix.postRotate(90f)

        val scaledBitmap = Bitmap.createScaledBitmap(this, width, height, true)

        return Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
    }

}