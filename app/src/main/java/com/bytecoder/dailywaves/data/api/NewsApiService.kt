package com.bytecoder.dailywaves.data.api

import com.bytecoder.dailywaves.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    // top headlines us
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    // everything (for India news)
    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}
