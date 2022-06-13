package com.mask.game.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix

object BitmapMerger {

    operator fun invoke(bitmap0: Bitmap, bitmap1: Bitmap): Bitmap {
        val bmOverlay = Bitmap.createBitmap(bitmap0.width, bitmap0.height, bitmap0.config)
        val scaledPhotoBitmap = Bitmap.createScaledBitmap(bitmap1, bitmap0.width, bitmap0.height, true)

        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(bitmap0, Matrix(), null)
        canvas.drawBitmap(scaledPhotoBitmap, 0f, 0f, null)
        bitmap0.recycle()
        scaledPhotoBitmap.recycle()
        return bmOverlay
    }

}