package com.example.criticalnews.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criticalnewstest.Utils
import com.example.criticalnewstest.models.Article
import com.example.criticalnewstest.repositorys.NewsRepository
import com.example.criticalnewstest.models.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val sources: MutableLiveData<List<Source>> = MutableLiveData()

    val articles: MutableLiveData<List<Article>> = MutableLiveData()

    init {
        getSources()
    }

    private fun getSources() = viewModelScope.launch {
        val sourcesList = newsRepository.getAllSources()
        Log.d("NewsViewModel", "Received ${sourcesList.size} sources")
        sources.postValue(sourcesList)
    }

    fun getTopHeadlinesBySource(source: String) = viewModelScope.launch {
        val articleList = newsRepository.getTopHeadlinesBySource(source)
        Log.d("NewsViewModel", "Received ${articleList.size} articles")

        for (article in articleList) {
            article.publishedAt = Utils.formatDate(article.publishedAt, "yyyy-MM-dd", "dd-MM-yyyy")
        }

        articles.postValue(articleList)
    }
}