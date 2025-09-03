package com.bytecoder.dailywaves.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytecoder.dailywaves.data.model.Article
import com.bytecoder.dailywaves.data.repository.NewsRepository
import com.bytecoder.dailywaves.util.Resource
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _articles = MutableLiveData<Resource<List<Article>>>()
    val articles: LiveData<Resource<List<Article>>> = _articles

    // Load Indian news with "everything?q=india"
    fun loadIndianNews() {
        _articles.value = Resource.Loading
        viewModelScope.launch {
            when (val res = repository.fetchIndianNews()) {
                is Resource.Success -> _articles.postValue(Resource.Success(res.data.articles))
                is Resource.Error -> _articles.postValue(Resource.Error(res.message))
                is Resource.Loading -> Unit
            }
        }
    }

    // load top us news
    fun loadTopHeadlines(country: String = "us") {
        _articles.value = Resource.Loading
        viewModelScope.launch {
            when (val res = repository.fetchTopHeadlines(country)) {
                is Resource.Success -> _articles.postValue(Resource.Success(res.data.articles))
                is Resource.Error -> _articles.postValue(Resource.Error(res.message))
                is Resource.Loading -> Unit
            }
        }
    }
}
