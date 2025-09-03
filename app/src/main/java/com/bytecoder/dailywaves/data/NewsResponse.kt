package com.bytecoder.dailywaves.data

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)
data class News(
    val title: String,
    val source: String,
    val imageUrl: String
)
