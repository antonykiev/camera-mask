package com.mask.game.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskCameraAccessBinding
import permissions.dispatcher.ktx.constructPermissionsRequest

class FragmentMaskCameraAccess : Fragment(R.layout.fragment_mask_camera_access) {

    private val binding by viewBinding(FragmentMaskCameraAccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            showPermissionRequest()
        }
    }

    private fun showPermissionRequest() {
        val request = constructPermissionsRequest(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            onShowRationale = { it.proceed() },
            onPermissionDenied = { Toast.makeText(requireContext(), getString(R.string.on_permission_denied), Toast.LENGTH_SHORT).show() },
            onNeverAskAgain = { Toast.makeText(requireContext(), getString(R.string.on_never_ask_again), Toast.LENGTH_SHORT).show() }
        ) {
            binding.btnNext.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.root, FragmentMaskGame())
                    .addToBackStack("")
                    .commit()
            }
        }
        request.launch()
    }
}