package com.mask.game.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mask.game.R
import com.mask.game.viewmodels.ViewModelMask

class FragmentMaskCongratulation: Fragment() {

//    private val binding by viewBinding(FragmentCardCongratulationsBinding::bind)

    private val viewModelGame by activityViewModels<ViewModelMask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback {
            //do nothing
        }

//        viewModelGame.scoreCounter.observe(viewLifecycleOwner) {
//            val scoreSteps = 50
//            binding.tvResult.text = "${it.size * scoreSteps}"
//        }
//
//        binding.btnNext.setOnClickListener {
//
//        }
    }

}