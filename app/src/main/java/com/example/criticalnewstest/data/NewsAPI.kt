package com.example.criticalnewstest.data

import com.example.criticalnewstest.utilities.Constants.Companion.API_KEY
import com.example.criticalnewstest.models.NewsResponse
import com.example.criticalnewstest.models.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey")
        apiKey: String = API_KEY,
        ): Response<SourcesResponse>

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesBySource(
        @Query("sources")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}