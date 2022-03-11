package com.example.den.nasapicture.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.den.nasapicture.model.RoverPhotos
import com.example.den.nasapicture.repository.NASAReporitory
import kotlinx.coroutines.flow.Flow

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val repository = NASAReporitory.getInstance()

    init {
        Log.i(TAG,"Creating MainViewModel")
    }
    fun fetchNASAImages(): Flow<PagingData<RoverPhotos.Photo>> {
        return repository.letNASAImagesFlow().cachedIn(viewModelScope)
    }
}