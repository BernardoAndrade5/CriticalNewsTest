package com.example.criticalnewstest

import NewsAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.criticalnews.viewmodels.NewsViewModel
import com.example.criticalnewstest.adapters.SourceSpinnerAdapter
import com.example.criticalnewstest.databinding.ActivityMainBinding
import com.example.criticalnewstest.models.Source
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillSourcesAdapter()
        fillHeadlinesAdapterAndTopHeadline()
        getSelectedSource()

    }

    private fun fillSourcesAdapter(){
        viewModel.sources.observe(this){sources ->
            binding.apply {
                if (sources.isNotEmpty()){
                    dropdownMenuSources.adapter = SourceSpinnerAdapter(this@MainActivity, sources)
                }
                else{
                    dropdownMenuSources.adapter = SourceSpinnerAdapter(this@MainActivity, emptyList())
                }
            }

        }
    }

    private fun fillHeadlinesAdapterAndTopHeadline(){
        binding.topHeadlinesRv.layoutManager = LinearLayoutManager(this)
        viewModel.articles.observe(this) { articles ->
            binding.apply {
                if (articles.isNotEmpty()) {
                    Glide.with(this@MainActivity)
                        .load(articles.firstOrNull()?.urlToImage)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.topHeadlineImage)
                    topHeadlineNewTitle.text = articles.firstOrNull()?.title
                    topHeadlineNewDescription.text = articles.firstOrNull()?.description
                    topHeadlineNewDate.text = articles.firstOrNull()?.publishedAt
                    topHeadlineNewSource.text = articles.firstOrNull()?.source?.name
                    topHeadlinesRv.adapter = NewsAdapter(articles.drop(1))
                } else {
                    topHeadlineNewTitle.text = ""
                    topHeadlineNewDescription.text = ""
                    topHeadlineNewDate.text = ""
                    topHeadlineNewSource.text = ""
                }
            }
        }
    }

    private fun getSelectedSource(){
        binding.dropdownMenuSources.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSource = parent?.getItemAtPosition(position) as? Source
                Log.d("MainActivity", "Selected source: $selectedSource")
                selectedSource?.id?.let { sourceId ->
                    viewModel.getTopHeadlinesBySource(sourceId)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO : Source is always selected
            }
        }
    }
}