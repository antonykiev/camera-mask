package com.mask.game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mask.game.R

class ActivityMaskGame : AppCompatActivity(R.layout.activity_mask_game) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, FragmentMaskRules())
            .commit()
    }
}