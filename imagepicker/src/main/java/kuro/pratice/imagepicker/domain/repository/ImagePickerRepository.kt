package kuro.pratice.imagepicker.domain.repository

import androidx.paging.PagingData
import kuro.pratice.imagepicker.data.entity.Album
import kuro.pratice.imagepicker.data.entity.Photo
import kotlinx.coroutines.flow.Flow

interface ImagePickerRepository {

    fun getAlbums(): Flow<List<Album>>

    fun getPhotos(album: Album): Flow<PagingData<Photo>>
}