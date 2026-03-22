package com.devbymeco.cursotestingandroid.di

import com.devbymeco.cursotestingandroid.BuildConfig
import com.devbymeco.cursotestingandroid.productlist.data.remote.MiniMarketApiService
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


object NetworkModule {
    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String{
        return "https://raw.githubusercontent.com/ArisGuimera/minimarket-api/main/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG){
            builder.addInterceptor(loggingInterceptor)
        }

        return builder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @Singleton
    fun provideJson(): Json{
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideMiniMarketApiService(retrofit: Retrofit): MiniMarketApiService{
        return retrofit.create(MiniMarketApiService::class.java)
    }

}