package com.anusha.nytimes.model

data class FormatedPopularArticle(
    val id: Long,
    val publish_date : String,
    val url: String ,
    val byline: String,
    val title: String ,
    val abstract: String ,
    val imageUrl: String? ,
    val imageUrlLarge: String? = null
)