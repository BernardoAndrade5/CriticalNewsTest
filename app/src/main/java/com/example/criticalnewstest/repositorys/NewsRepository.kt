package com.example.criticalnewstest.repositorys


import android.util.Log
import com.example.criticalnewstest.data.NewsAPI
import com.example.criticalnewstest.models.Article
import com.example.criticalnewstest.models.Source
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsAPI){

    suspend fun getAllSources(): List<Source> {
        val response = newsApi.getSources()
        if (response.isSuccessful) {
            val newsResponse = response.body()
            if (newsResponse != null) {
                return newsResponse.sources
            }
        }
        return emptyList()
    }

    suspend fun getTopHeadlinesBySource(source : String): List<Article> {
        val response = newsApi.getTopHeadlinesBySource(source)
        Log.d("NewsRepository", response.toString())
        if(response.isSuccessful){
            val newsResponse = response.body()
            if(newsResponse != null){
                val articlesListSorted = newsResponse.articles
                    .filter { it.source.id == source }
                    .sortedByDescending { it.publishedAt }
                return articlesListSorted
            }
        }
        return emptyList()
    }
}