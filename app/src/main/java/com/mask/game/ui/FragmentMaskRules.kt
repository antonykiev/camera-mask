package com.mask.game.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskRulesBinding
import permissions.dispatcher.ktx.constructPermissionsRequest

class FragmentMaskRules: Fragment(R.layout.fragment_mask_rules) {

    private val binding by viewBinding(FragmentMaskRulesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {

        }

        binding.btnNext.setOnClickListener {
            val request = constructPermissionsRequest(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onShowRationale = { it.proceed() },
                onPermissionDenied = { onPermissionCanceled() },
                onNeverAskAgain = { onPermissionCanceled() }
            ) {
                onPermissionGranted()
            }
            request.launch()
        }
    }

    private fun onPermissionCanceled() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, FragmentMaskCameraAccess())
            .addToBackStack("")
            .commit()
    }

    private fun onPermissionGranted() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, FragmentMaskGame())
            .addToBackStack("")
            .commit()
    }
}