package com.anusha.nytimes.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anusha.nytimes.R
import com.anusha.nytimes.utils.Constants
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity :  AppCompatActivity(){

    private var articleUrl : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        intent?.getStringExtra(Constants.ARTICLE_URL)?.let { articleUrl = it }
        articleUrl?.let { articlesWebView.loadUrl(it) }

    }

}