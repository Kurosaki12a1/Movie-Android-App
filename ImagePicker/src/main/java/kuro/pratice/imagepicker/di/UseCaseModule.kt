package kuro.pratice.imagepicker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kuro.pratice.imagepicker.domain.repository.ImagePickerRepository
import kuro.pratice.imagepicker.domain.usecase.GetAlbumsUseCase
import kuro.pratice.imagepicker.domain.usecase.GetPhotosUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetAlbumsUseCase(repo: ImagePickerRepository): GetAlbumsUseCase {
        return GetAlbumsUseCase(repo)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPhotosUseCase(repo: ImagePickerRepository): GetPhotosUseCase {
        return GetPhotosUseCase(repo)
    }
}