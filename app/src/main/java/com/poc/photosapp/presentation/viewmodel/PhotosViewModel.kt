package com.poc.photosapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.photosapp.utilities.Resource
import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.domain.usecases.PhotosUseCase
import com.poc.photosapp.presentation.ui.interfaces.RetryCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val photosUseCase: PhotosUseCase) :
    ViewModel() {

    private val _photos = MutableStateFlow<Resource<List<PhotoItem>>>(Resource.loading())
    val photos: Flow<Resource<List<PhotoItem>>> get() = _photos


    init {
        getPhotos()
    }

    private fun getPhotos(isForced: Boolean = false) = viewModelScope.launch {
        flowOf(photosUseCase.invoke(isForced))
            .onStart { _photos.emit(Resource.loading()) }
            .catch { _photos.emit(Resource.error(error = it)) }
            .collect { _photos.emit(Resource.success(response = it)) }
    }

    fun retry(): RetryCallBack {
        return object : RetryCallBack {
            override fun invoke() {
                getPhotos(isForced = true)
            }
        }
    }

}