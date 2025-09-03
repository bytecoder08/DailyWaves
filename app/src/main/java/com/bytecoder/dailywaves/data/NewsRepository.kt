package com.bytecoder.dailywaves.data

class NewsRepository {
    suspend fun getTopHeadlines(apiKey: String): List<Article> {
        return RetrofitInstance.api.getTopHeadlines(apiKey = apiKey).articles
    }
}
