package com.bytecoder.dailywaves.ui

import androidx.lifecycle.*
import com.bytecoder.dailywaves.data.Article
import com.bytecoder.dailywaves.data.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    fun fetchNews(apiKey: String) {
        viewModelScope.launch {
            try {
                _articles.value = repository.getTopHeadlines(apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
