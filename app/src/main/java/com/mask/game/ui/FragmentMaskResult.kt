package com.mask.game.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskResultBinding
import com.mask.game.utils.BitmapToUriProvider
import com.mask.game.viewmodels.ViewModelMask


class FragmentMaskResult: Fragment(R.layout.fragment_mask_result) {

    private val binding by viewBinding(FragmentMaskResultBinding::bind)

    private val viewModelGame by activityViewModels<ViewModelMask>()

    private val btnList by lazy { listOf(
        binding.btnBack,
        binding.btnDone,
        binding.btnShare,
        binding.btnSave
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelGame.maskResult.observe(viewLifecycleOwner, ::onBitMapLoaded)
        viewModelGame.savingState.observe(viewLifecycleOwner, ::handleSavingState)
    }

    private fun onBitMapLoaded(bitmap: Bitmap) {

        binding.imgMaskResult.setImageBitmap(bitmap)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnShare.setOnClickListener {
            shareBitmap(bitmap)
            viewModelGame.onShared()
        }

        binding.btnDone.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, FragmentMaskCongratulation())
                .commit()
        }

        binding.btnSave.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModelGame.saveMask(bitmap, requireContext())
        }
    }

    private fun handleSavingState(state: ViewModelMask.SavingState) {
        println(state)
        when (state) {
            ViewModelMask.SavingState.Saving -> {
                binding.btnDone.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                btnList.forEach {
                    it.isClickable = false
                }
            }
            ViewModelMask.SavingState.Saved -> {
                binding.btnDone.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                btnList.forEach {
                    it.isClickable = true
                }
                Snackbar.make(
                    binding.root,
                    getString(R.string.success_saving_mask),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            ViewModelMask.SavingState.Nothing -> {
                binding.btnDone.visibility = View.GONE
                binding.progressBar.visibility = View.INVISIBLE
                btnList.forEach {
                    it.isClickable = true
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.btnDone.visibility = View.VISIBLE
    }

    private fun shareBitmap(imgScreenShot: Bitmap) {
        val uri = BitmapToUriProvider.invoke(requireActivity(), imgScreenShot)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share_image)), 1)
    }

}