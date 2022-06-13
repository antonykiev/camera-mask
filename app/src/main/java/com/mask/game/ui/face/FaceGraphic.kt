package com.mask.game.ui.face

import android.graphics.*
import com.mask.game.ui.camera.GraphicOverlay

import com.google.android.gms.vision.face.Face;
import com.mask.game.R


class FaceGraphic(overlay: GraphicOverlay): GraphicOverlay.Graphic(overlay) {
    private val BOX_STROKE_WIDTH = 5.0f

    private var mBoxPaint: Paint = Paint()

    @Volatile
    private var mFace: Face? = null
    private var bitmap: Bitmap? = null
    var op: Bitmap? = null

    init {
        mBoxPaint
        mBoxPaint.color = Color.BLUE
        mBoxPaint.style = Paint.Style.STROKE
        mBoxPaint.strokeWidth = BOX_STROKE_WIDTH
        bitmap = BitmapFactory.decodeResource(overlay.context.resources, R.drawable.mask_dracon_face)
        op = bitmap
    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    fun updateFace(face: Face) {
        mFace = face
        op = Bitmap.createScaledBitmap(
            bitmap!!,
            scaleX(face.width).toInt(),
            scaleY(bitmap!!.height * face.width / bitmap!!.width).toInt(),
            false
        )
        postInvalidate()
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    override fun draw(canvas: Canvas?) {
        val face: Face = mFace ?: return

        // Draws a circle at the position of the detected face, with the face's track id below.
        val x = translateX(face.position.x + face.width / 2)
        val y = translateY(face.position.y + face.height / 2)
        val xOffset = scaleX(face.width / 2.0f)
        val yOffset = scaleY(face.height / 2.0f)
        val left = x - xOffset
        val top = y - yOffset

        /**
         * if debug uncomment
         */
        val right = x + xOffset
        val bottom = y + yOffset
        canvas?.drawRect(left, top, right, bottom, mBoxPaint!!)
        canvas?.drawBitmap(op!!, left, top, Paint())
    }

}