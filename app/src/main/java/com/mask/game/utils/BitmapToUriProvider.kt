package com.mask.game.utils

import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.mask.game.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapToUriProvider {

    operator fun invoke(contextWrapper: ContextWrapper, bmp: Bitmap?): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(contextWrapper.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

            val out = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(
                contextWrapper,
                "${BuildConfig.APPLICATION_ID}.provider",
                file
            )

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

}