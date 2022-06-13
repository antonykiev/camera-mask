package com.mask.game.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.*

class ViewModelMask: ViewModel() {

    private val _maskResult = MutableLiveData<Bitmap>()
    val maskResult: LiveData<Bitmap> = _maskResult

    val scoreCounter = MutableLiveData<HashSet<ScoreState>>(hashSetOf())


    fun setMask(bitmap: Bitmap) {
        _maskResult.postValue(bitmap)
    }


    private fun incrementScore(state: ScoreState) {
        val currentScore = scoreCounter.value ?: hashSetOf()
        currentScore.add(state)
        scoreCounter.postValue(currentScore)
    }


    fun onSaved() {
        incrementScore(ScoreState.Saved)
    }

    fun onShared() {
        incrementScore(ScoreState.Shared)
    }

    enum class ScoreState {
        Saved,
        Shared
    }


}