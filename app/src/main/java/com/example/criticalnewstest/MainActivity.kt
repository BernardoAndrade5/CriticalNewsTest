package com.example.criticalnewstest

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.criticalnews.viewmodels.NewsViewModel
import com.example.criticalnewstest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.articles.observe(this) { articles ->
            binding.apply {
                if (articles.isNotEmpty()) {
                    topHeadlineNewTitle.text = articles.firstOrNull()?.title
                    topHeadlineNewDescription.text = articles.firstOrNull()?.description
                    topHeadlineNewDate.text = articles.firstOrNull()?.publishedAt
                    topHeadlineNewSource.text = articles.firstOrNull()?.source?.name
                } else {
                    topHeadlineNewTitle.text = ""
                    topHeadlineNewDescription.text = ""
                    topHeadlineNewDate.text = ""
                    topHeadlineNewSource.text = ""
                }
            }
        }
    }
}