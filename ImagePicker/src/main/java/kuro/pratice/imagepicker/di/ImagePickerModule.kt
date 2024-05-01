package kuro.pratice.imagepicker.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ImagePickerModule {

    @Provides
    @ViewModelScoped
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver
}