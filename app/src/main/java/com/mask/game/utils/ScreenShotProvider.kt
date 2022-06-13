package com.mask.game.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

object ScreenShotProvider {

    operator fun invoke(view: View): Bitmap {
        val bitmap: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

}