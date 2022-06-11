package com.mask.game.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskGameBinding

class FragmentMaskGame: Fragment(R.layout.fragment_mask_game) {

    private val binding by viewBinding(FragmentMaskGameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}