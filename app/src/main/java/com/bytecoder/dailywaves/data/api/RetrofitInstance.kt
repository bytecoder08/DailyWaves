package com.bytecoder.dailywaves.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.bytecoder.dailywaves.BuildConfig

object RetrofitInstance {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private val authInterceptor = Interceptor { chain ->
        // pass apiKey explicitly from repository to keep control; this can be used for common headers if needed.
        chain.proceed(chain.request())
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .build()

    val api: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApiService::class.java)
    }

    val apiKey: String get() = BuildConfig.NEWS_API_KEY
}