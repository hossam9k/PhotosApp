package com.poc.photosapp.domain.usecases

import com.poc.photosapp.core.entities.PhotoItem

interface PhotosUseCase {

    suspend operator fun invoke(isForceUpdate: Boolean = false): List<PhotoItem>

}