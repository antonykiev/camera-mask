package com.mask.game.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskGameBinding
import com.mask.game.ui.camera.GraphicOverlay
import com.mask.game.ui.face.FaceGraphic
import java.io.IOException


class FragmentMaskGame: Fragment(R.layout.fragment_mask_game) {

    private var mCameraSource: CameraSource? = null
    private val RC_HANDLE_GMS = 9001


    private val binding by viewBinding(FragmentMaskGameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createCameraSource() {
        val detector: FaceDetector = FaceDetector.Builder(requireContext())
            .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
            .setLandmarkType(FaceDetector.ALL_LANDMARKS)
            .setMode(FaceDetector.ACCURATE_MODE)
            .build()

        val processor = MultiProcessor.Builder<Face>(GraphicFaceTrackerFactory()).build()

        detector.setProcessor(processor)

        if (!detector.isOperational) {

        }

        mCameraSource = CameraSource.Builder(context, detector)
            .setRequestedPreviewSize(640, 480)
            .setFacing(CameraSource.CAMERA_FACING_FRONT)
            .setRequestedFps(30.0f)
            .build()
    }

    private fun startCameraSource() {
        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())
        if (code != ConnectionResult.SUCCESS) {
            val dlg: Dialog = GoogleApiAvailability.getInstance().getErrorDialog(requireActivity(), code, RC_HANDLE_GMS)
            dlg.show()
        }
        if (mCameraSource != null) {
            try {
                binding.preview.start(mCameraSource, binding.faceOverlay)
            } catch (e: IOException) {
                println("Unable to start camera source.")
                mCameraSource!!.release()
                mCameraSource = null
            }
        }
    }

    /**
     * Restarts the camera.
     */
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    /**
     * Stops the camera.
     */
    override fun onPause() {
        super.onPause()
        binding.preview.stop()
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    override fun onDestroy() {
        super.onDestroy()
        mCameraSource?.let { it.release() }
    }

    //***********************************************************************************

    private inner class GraphicFaceTrackerFactory : MultiProcessor.Factory<Face> {
        override fun create(face: Face): Tracker<Face> {
            return GraphicFaceTracker(binding.faceOverlay)
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private inner class GraphicFaceTracker(private val mOverlay: GraphicOverlay) : Tracker<Face>() {

        private val mFaceGraphic: FaceGraphic = FaceGraphic(mOverlay)

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        override fun onNewItem(faceId: Int, item: Face?) {
            mFaceGraphic.setId(faceId)
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        override fun onUpdate(detectionResults: Detections<Face>, face: Face) {
            mOverlay.add(mFaceGraphic)
            mFaceGraphic.updateFace(face)
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        override fun onMissing(detectionResults: Detections<Face>) {
            mOverlay.remove(mFaceGraphic)
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        override fun onDone() {
            mOverlay.remove(mFaceGraphic)
        }
    }
}