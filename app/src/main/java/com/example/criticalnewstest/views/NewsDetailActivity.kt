package com.example.criticalnewstest.views

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.criticalnewstest.databinding.ActivityNewsDetailBinding
import com.example.criticalnewstest.models.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity(){

    lateinit var binding  : ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article: Article? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("article", Article::class.java)
        } else {
            intent.getParcelableExtra("article")
        }

        article?.let { getSelectedArticle(it) }

    }

    private fun getSelectedArticle(article: Article){
        binding.apply {
            Glide.with(this@NewsDetailActivity)
                .load(article.urlToImage)
                .placeholder(android.R.drawable.stat_notify_sync)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.topHeadlineImage)
            topHeadlineNewTitle.text = article.title
            topHeadlineNewDescription.text = article.description
            topHeadlineNewContent.text = article.content
            topHeadlineNewDate.text = article.publishedAt
            sourceName.text = article.source.name
        }
    }
}