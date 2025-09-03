package com.bytecoder.dailywaves.data.repository

import com.bytecoder.dailywaves.data.api.RetrofitInstance
import com.bytecoder.dailywaves.data.model.NewsResponse
import com.bytecoder.dailywaves.util.Resource

class NewsRepository {
    // India news "everything"
    suspend fun fetchIndianNews(): Resource<NewsResponse> {
        return try {
            val response = RetrofitInstance.api.getEverything(
                query = "india",
                page = 1,
                pageSize = 20,
                apiKey = RetrofitInstance.apiKey
            )
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Network error")
        }
    }

    //top headlines us
    suspend fun fetchTopHeadlines(country: String = "us"): Resource<NewsResponse> {
        return try {
            val response = RetrofitInstance.api.getTopHeadlines(
                country = country,
                page = 1,
                pageSize = 20,
                apiKey = RetrofitInstance.apiKey
            )
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Network error")
        }
    }
}
