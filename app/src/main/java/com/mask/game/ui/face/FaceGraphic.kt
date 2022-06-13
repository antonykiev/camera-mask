package com.mask.game.ui.face

import android.graphics.*
import com.mask.game.ui.camera.GraphicOverlay

import com.google.android.gms.vision.face.Face;
import com.mask.game.R


class FaceGraphic(overlay: GraphicOverlay): GraphicOverlay.Graphic(overlay) {
    private val FACE_POSITION_RADIUS = 10.0f
    private val ID_TEXT_SIZE = 40.0f
    private val ID_Y_OFFSET = 50.0f
    private val ID_X_OFFSET = -50.0f
    private val GENERIC_POS_OFFSET = 20.0f
    private val GENERIC_NEG_OFFSET = -20.0f

    private val BOX_STROKE_WIDTH = 5.0f

    private val COLOR_CHOICES = intArrayOf(
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.WHITE,
        Color.YELLOW
    )
    private var mCurrentColorIndex = 0

    private var mFacePositionPaint: Paint? = null
    private var mIdPaint: Paint? = null
    private var mBoxPaint: Paint? = null

    @Volatile
    private var mFace: Face? = null
    private var mFaceId = 0
    private val mFaceHappiness = 0f
    private var bitmap: Bitmap? = null
    private var op: Bitmap? = null

    init {
        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.size
        val selectedColor = COLOR_CHOICES[mCurrentColorIndex]
        mFacePositionPaint = Paint()
        mFacePositionPaint!!.color = selectedColor
        mIdPaint = Paint()
        mIdPaint!!.color = selectedColor
        mIdPaint!!.textSize = ID_TEXT_SIZE
        mBoxPaint = Paint()
        mBoxPaint!!.color = selectedColor
        mBoxPaint!!.style = Paint.Style.STROKE
        mBoxPaint!!.strokeWidth = BOX_STROKE_WIDTH
        bitmap = BitmapFactory.decodeResource(overlay.context.resources, R.drawable.mask_dracon_face)
        op = bitmap
    }

    fun setId(id: Int) {
        mFaceId = id
    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    fun updateFace(face: Face) {
        mFace = face
        op = Bitmap.createScaledBitmap(
            bitmap!!, scaleX(face.width).toInt(),
            scaleY(bitmap!!.height * face.width / bitmap!!.width).toInt(), false
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
//        val right = x + xOffset
//        val bottom = y + yOffset
//        canvas?.drawRect(left, top, right, bottom, mBoxPaint!!)
        canvas?.drawBitmap(op!!, left, top, Paint())
    }

    private fun getNoseAndMouthDistance(nose: PointF, mouth: PointF): Float {
        return Math.hypot((mouth.x - nose.x).toDouble(), (mouth.y - nose.y).toDouble()).toFloat()
    }
}