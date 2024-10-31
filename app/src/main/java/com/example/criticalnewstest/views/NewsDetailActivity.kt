package com.example.criticalnewstest.views

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.criticalnews.viewmodels.NewsViewModel
import com.example.criticalnewstest.R
import com.example.criticalnewstest.databinding.ActivityNewsDetailBinding
import com.example.criticalnewstest.models.Article
import com.example.criticalnewstest.models.Source
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

    fun getSelectedArticle(article: Article){
        binding.apply {
            Glide.with(this@NewsDetailActivity)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.topHeadlineImage)
            topHeadlineNewTitle.text = article.title
            topHeadlineNewDescription.text = article.description
            topHeadlineNewContent.text = article.content
            topHeadlineNewDate.text = article.publishedAt
            sourceName.text = article.source.name
        }
    }
}