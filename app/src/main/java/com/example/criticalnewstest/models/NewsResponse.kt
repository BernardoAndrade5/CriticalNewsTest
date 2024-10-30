package com.example.criticalnewstest.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class NewsResponse (
    val status : String,
    val totalResults : Int,
    val articles : List<Article>
)

@Parcelize
data class Article(
    val source : ArticleSource,
    val author : String?,
    val title : String?,
    val description : String?,
    val url : String?,
    val urlToImage : String?,
    var publishedAt : String?,
    val content : String?
) : Parcelable

@Parcelize
data class ArticleSource(
    val id : String?,
    val name : String?
) : Parcelable