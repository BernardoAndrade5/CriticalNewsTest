package com.example.criticalnews.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criticalnewstest.models.Article
import com.example.criticalnewstest.repositorys.NewsRepository
import com.example.criticalnewstest.models.SourcesResponse
import com.example.criticalnewstest.models.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.text.format

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val sources: MutableLiveData<List<Source>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: SourcesResponse? = null

    val articles: MutableLiveData<List<Article>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: SourcesResponse? = null

    init {
        getBreakingNews("us")
        getTopHeadlinesBySource("bbc-news")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        val sourcesList = newsRepository.getSources("business", "en", "us")
        Log.d("NewsViewModel", "Received ${sourcesList.size} sources")
        sources.postValue(sourcesList)
    }

    private fun getTopHeadlinesBySource(source: String) = viewModelScope.launch {
        val articleList = newsRepository.getTopHeadlinesBySource(source)
        Log.d("NewsViewModel", "Received ${articleList.size} sources")

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        for (article in articleList) {
            try {
                val date = inputDateFormat.parse(article.publishedAt)
                val formattedDate = outputDateFormat.format(date)
                article.publishedAt = formattedDate
            } catch (e: ParseException) {
                Log.e("NewsViewModel", "Error parsing date: ${e.message}")
            }
        }

        articles.postValue(articleList)
    }
}