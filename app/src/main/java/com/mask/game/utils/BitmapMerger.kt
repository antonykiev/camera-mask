package com.mask.game.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix


object BitmapMerger {

    operator fun invoke(bitmap0: Bitmap, bitmap1: Bitmap): Bitmap {
        val bmOverlay = Bitmap.createBitmap(bitmap0.width, bitmap0.height, bitmap0.config)
        val scaledPhotoBitmap = Bitmap.createScaledBitmap(bitmap1, bitmap0.width, bitmap0.height, true)

        val cx = bmOverlay.width / 2f
        val cy = bmOverlay.height / 2f
        val flippedBitmap = bitmap0.flip(-1f, 1f, cx, cy)

        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(flippedBitmap, Matrix(), null)
        canvas.drawBitmap(scaledPhotoBitmap, 0f, 0f, null)
        bitmap0.recycle()
        scaledPhotoBitmap.recycle()
        return bmOverlay
    }

    private fun Bitmap.flip(x: Float, y: Float, cx: Float, cy: Float): Bitmap {
        val matrix = Matrix().apply { postScale(x, y, cx, cy) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

}