package kuro.pratice.imagepicker.di

import android.content.ContentResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kuro.pratice.imagepicker.data.repository.ImagePickerRepositoryImpl
import kuro.pratice.imagepicker.domain.repository.ImagePickerRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideImagePickerRepository(
        contentResolver: ContentResolver
    ): ImagePickerRepository = ImagePickerRepositoryImpl(contentResolver)
}