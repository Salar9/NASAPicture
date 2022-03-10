package com.example.den.nasapicture.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.den.nasapicture.model.RoverPhotos
import com.example.den.nasapicture.repository.NASAReporitory
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    val repository = NASAReporitory.getInstance()

    fun fetchNASAImages(): Flow<PagingData<RoverPhotos.Photo>> {
        return repository.letNASAImagesFlow()
    }
}