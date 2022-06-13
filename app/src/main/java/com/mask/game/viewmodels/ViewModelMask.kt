package com.mask.game.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.*

class ViewModelMask: ViewModel() {

    private val _maskResult = MutableLiveData<Bitmap>()
    val maskResult: LiveData<Bitmap> = _maskResult

    fun setMask(bitmap: Bitmap) {
        _maskResult.postValue(bitmap)
    }


}