package com.anusha.nytimes.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anusha.nytimes.BuildConfig
import com.anusha.nytimes.model.*
import com.anusha.nytimes.network.DataProvider
import com.anusha.nytimes.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PopularArticlesListViewModel :BaseViewModel() {

    val selectedArticle = MutableLiveData<FormatedPopularArticle>()


    private val _popularArticles = MutableLiveData<PopularArticles>()

    private val _formatedPopularArticles = MutableLiveData<List<FormatedPopularArticle>>()
    val formatedPopularArticles: LiveData<List<FormatedPopularArticle>> = _formatedPopularArticles

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadPopularArtiles() {
        launch {
            DataProvider.getPopularArticles(
                Constants.ALL_SECTIONS,
                Constants.PERIOD,
                BuildConfig.API_KEY
            ).checkResultSusp(
                success = { response ->
                    _popularArticles.value = response
                    _formatedPopularArticles.value = response.results.map { mapToArticles(it) }
                },
                error = {
                    _error.value = it.errorResponse.message.toString()
                    Log.d("error", it.errorResponse.message.toString())
                    it.printStackTrace()
                })
        }
    }

    private fun mapToArticles(result: Result) : FormatedPopularArticle{
        return with(result){
            FormatedPopularArticle(
                id = this.id,
                title = this.title,
                abstract = this.abstract,
                publish_date = this.publishedDate,
                url = this.url,
                byline = this.byline,
                imageUrl = getImageUrl(this.media),
                imageUrlLarge = getLargeImageUrl(this.media)
            )
        }
    }

    private fun getImageUrl(media: List<Media>): String? {
        return if (media.isNotEmpty()
            && media[0].type == "image"
            && !media[0].mediaMetadata.isNullOrEmpty()){
            return media[0].mediaMetadata[0].url
        }
        else
           null
    }

    private fun getLargeImageUrl(media: List<Media>): String? {
        return if (media.isNotEmpty()
            && media[0].type == "image"
            && !media[0].mediaMetadata.isNullOrEmpty()
            && media[0].mediaMetadata.size >= 3){
            return media[0].mediaMetadata[2].url
        }
        else
            null
    }
}

