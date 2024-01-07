package com.poc.photosapp.core.usecases

import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.domain.repository.PhotosRepository
import com.poc.photosapp.domain.usecases.PhotosUseCase
import javax.inject.Inject

class PhotosUseCaseImp @Inject constructor(private val photosRepository: PhotosRepository) :
    PhotosUseCase {
    override suspend fun invoke(isForceUpdate: Boolean): List<PhotoItem> {
        return photosRepository.getPhotos(isForceUpdate)
    }

}