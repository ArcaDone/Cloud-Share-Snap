package com.arcadone.cloudsharesnap.di

import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.CatBoxFileUploader
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.DefaultImageCompressor
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.FileUploader
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.ImageCompressor
import com.arcadone.cloudsharesnap.rest.CatBoxApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageCompressor(): ImageCompressor = DefaultImageCompressor()

    @Provides
    @Singleton
    fun provideFileUploader(catBoxApiService: CatBoxApiService): FileUploader =
        CatBoxFileUploader(catBoxApiService)

}