package com.example.criticalnewstest.repositorys


import android.util.Log
import com.example.criticalnewstest.data.NewsAPI
import com.example.criticalnewstest.models.Article
import com.example.criticalnewstest.models.SourcesResponse
import com.example.criticalnewstest.models.Source
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsAPI){

    suspend fun getSources(category: String, language: String, country: String): List<Source> {
        val response = newsApi.getSources(category, language, country)
        if (response.isSuccessful) {
            val newsResponse = response.body()
            if (newsResponse != null) {
                Log.d("NewsRepository", "Received ${newsResponse.sources.size} sources")
                return newsResponse.sources.filter { it.category == category }
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
                return newsResponse.articles.filter { it.source.id == source }
            }
        }
        return emptyList()
    }
}