package com.mask.game.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.mask.game.utils.SaveImageProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelMask: ViewModel() {

    private var currentJob: Job? = null

    private val _maskResult = MutableLiveData<Bitmap>()
    val maskResult: LiveData<Bitmap> = _maskResult

    private val _savingState = MutableLiveData(SavingState.Nothing)
    val savingState: LiveData<SavingState> = _savingState

    val scoreCounter = MutableLiveData<HashSet<ScoreState>>(hashSetOf())


    fun setMask(bitmap: Bitmap) {
        _maskResult.postValue(bitmap)
    }

    private fun incrementScore(state: ScoreState) {
        val currentScore = scoreCounter.value ?: hashSetOf()
        currentScore.add(state)
        scoreCounter.postValue(currentScore)
    }

    fun saveMask(bitmap: Bitmap, context: Context) {
        viewModelScope.launch {
            _savingState.value = SavingState.Saving
            delay(50)
            onSaved()
            SaveImageProvider.save(bitmap, context)
            _savingState.postValue(SavingState.Saved)
        }
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

    enum class SavingState {
        Nothing,
        Saving,
        Saved
    }

    override fun onCleared() {
        super.onCleared()
        if (currentJob?.isActive == true) {
            currentJob?.cancel()
        }
    }
}