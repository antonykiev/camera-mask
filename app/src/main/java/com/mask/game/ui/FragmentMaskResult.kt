package com.mask.game.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskGameBinding
import com.mask.game.databinding.FragmentMaskResultBinding
import com.mask.game.utils.BitmapToUriProvider
import com.mask.game.utils.SaveImageProvider
import com.mask.game.viewmodels.ViewModelMask

class FragmentMaskResult: Fragment(R.layout.fragment_mask_result) {

    private val binding by viewBinding(FragmentMaskResultBinding::bind)

    private val viewModelGame by activityViewModels<ViewModelMask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelGame.maskResult.observe(viewLifecycleOwner, ::onBitMapLoaded)
    }

    private fun onBitMapLoaded(bitmap: Bitmap) {

        binding.imgMaskResult.setImageBitmap(bitmap)

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
            SaveImageProvider.save(bitmap, requireContext())
            viewModelGame.onSaved()
        }

    }

    private fun shareBitmap(imgScreenShot: Bitmap) {
        val uri = BitmapToUriProvider.invoke(requireActivity(), imgScreenShot)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, getString(R.string.share_image)))
    }
}