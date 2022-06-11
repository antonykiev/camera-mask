package com.mask.game.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.databinding.FragmentMaskRulesBinding

class FragmentMaskRules: Fragment(R.layout.fragment_mask_rules) {

    private val binding by viewBinding(FragmentMaskRulesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {

        }

        binding.btnNext.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, FragmentMaskCameraAccess())
                .addToBackStack("")
                .commit()
        }
    }
}