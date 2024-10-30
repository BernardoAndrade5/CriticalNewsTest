package com.example.criticalnewstest.models

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    val sources: List<Source>
)

data class Source(
    val id: String?,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?
)