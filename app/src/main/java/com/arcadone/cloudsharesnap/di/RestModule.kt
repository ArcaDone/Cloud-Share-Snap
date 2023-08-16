package com.arcadone.cloudsharesnap.di

import android.app.Application
import android.content.Context
import com.arcadone.cloudsharesnap.domain.GetCountriesUseCase
import com.arcadone.cloudsharesnap.domain.GetCountriesUseCaseImpl
import com.arcadone.cloudsharesnap.domain.UploadToCatBoxUseCase
import com.arcadone.cloudsharesnap.domain.UploadToCatBoxUseCaseImpl
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.FileUploader
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.ImageCompressor
import com.arcadone.cloudsharesnap.rest.CatBoxAPIRepository
import com.arcadone.cloudsharesnap.rest.CatBoxAPIRepositoryImpl
import com.arcadone.cloudsharesnap.rest.CatBoxApiService
import com.arcadone.cloudsharesnap.rest.CountryService
import com.arcadone.cloudsharesnap.rest.CountriesAPIRepository
import com.arcadone.cloudsharesnap.rest.CountriesAPIRepositoryImpl
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RestModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun providesObjectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(KotlinModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Provides
    fun providesHttpClient(
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.MINUTES)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesConfigService(
        objectMapper: ObjectMapper,
        okHttpClient: OkHttpClient
    ): CountryService {
        return Retrofit.Builder()
            .baseUrl("https://api.photoforse.online/")
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create(CountryService::class.java)
    }

    @Provides
    @Singleton
    fun providesConfigRepository(
        countryService: CountryService,
    ): CountriesAPIRepository {
        return CountriesAPIRepositoryImpl(
            countryService = countryService,
        )
    }

    @Provides
    @Singleton
    fun providesCatBoxApiService(
        objectMapper: ObjectMapper,
        okHttpClient: OkHttpClient
    ): CatBoxApiService {
        return Retrofit.Builder()
            .baseUrl("https://catbox.moe/")
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create(CatBoxApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCatBoxAPIRepository(
        imageCompressor: ImageCompressor,
        fileUploader: FileUploader
    ): CatBoxAPIRepository {
        return CatBoxAPIRepositoryImpl(
            imageCompressor = imageCompressor,
            fileUploader = fileUploader
        )
    }
    @Provides
    @Singleton
    fun provideGetCountriesUseCase(
        countriesAPIRepository: CountriesAPIRepository
    ): GetCountriesUseCase =
        GetCountriesUseCaseImpl(countriesAPIRepository)

    @Provides
    @Singleton
    fun provideUploadImageToCatBoxUseCase(
        catBoxAPIRepository: CatBoxAPIRepository,
        context: Context
    ): UploadToCatBoxUseCase =
        UploadToCatBoxUseCaseImpl(catBoxAPIRepository, context)

}