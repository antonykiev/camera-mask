package com.mask.game.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskGameBinding
import com.mask.game.databinding.FragmentMaskResultBinding
import com.mask.game.viewmodels.ViewModelMask

class FragmentMaskResult: Fragment(R.layout.fragment_mask_result) {

    private val binding by viewBinding(FragmentMaskResultBinding::bind)

    private val viewModelGame by activityViewModels<ViewModelMask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelGame.maskResult.observe(viewLifecycleOwner) {
            binding.imgMaskResult.setImageBitmap(it)
        }

    }
}