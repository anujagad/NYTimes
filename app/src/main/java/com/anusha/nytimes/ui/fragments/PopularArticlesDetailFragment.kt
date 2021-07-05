package com.anusha.nytimes.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.anusha.nytimes.R
import com.anusha.nytimes.activity.WebViewActivity
import com.anusha.nytimes.utils.Constants
import com.anusha.nytimes.viewModels.PopularArticlesListViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_popular_articles_detail.*

class PopularArticlesDetailFragment : BaseFragment() {
    private var articleUrl  : String?= null
    override val layoutId: Int = R.layout.fragment_popular_articles_detail
    private val viewModel: PopularArticlesListViewModel by  activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.invalidateOptionsMenu()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        btnViewDetail.setOnClickListener {
            articleUrl.let {
                Intent(
                    requireContext(),
                    WebViewActivity::class.java)
                    .apply {
                        putExtra(Constants.ARTICLE_URL, articleUrl)
                        startActivity(this)
                    }
            }
        }
    }

    private fun initObservers() {
        viewModel.selectedArticle.observe(viewLifecycleOwner, Observer{
            it?.let { article ->
                tvTitle.text =  article.title
                tvAuthor.text = article.byline
                tvAbstract.text = article.abstract
                tvPublishedDate.text = article.publish_date
                val imageUrl = article.imageUrlLarge
                articleUrl =  article.url
                Glide.with(ivArticleImage)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivArticleImage)
            }
        })
    }
}

