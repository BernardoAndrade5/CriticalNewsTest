package com.example.criticalnewstest.views

import NewsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.criticalnews.viewmodels.NewsViewModel
import com.example.criticalnewstest.R
import com.example.criticalnewstest.adapters.SourceSpinnerAdapter
import com.example.criticalnewstest.databinding.ActivityMainBinding
import com.example.criticalnewstest.models.Source
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillSourcesAdapter()
        fillHeadlinesAdapterAndTopHeadline()
        getSelectedSource()
        handleBiometricLock()
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
                    topHeadlineLayout.setOnClickListener {
                        val firstArticle = articles.firstOrNull()
                        if (firstArticle != null) {
                            val intent = Intent(this@MainActivity, NewsDetailActivity::class.java)
                            intent.putExtra("article", firstArticle)
                            startActivity(intent)
                        }
                    }
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

    private fun handleBiometricLock(){
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@MainActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@MainActivity, "Authentication Successful", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Authentication")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

}