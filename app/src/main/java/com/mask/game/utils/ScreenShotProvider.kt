package com.mask.game.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.ExifInterface
import android.os.Build
import android.view.View
import android.widget.Toast


object ScreenShotProvider {

    operator fun invoke(view: View): Bitmap {

        val min: Int = Math.min(view.width, view.height)
        val max: Int = Math.max(view.width, view.height)

        val bitmap: Bitmap = Bitmap.createBitmap(min, max, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

}