package com.anusha.nytimes.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anusha.nytimes.R
import com.anusha.nytimes.model.FormatedPopularArticle
import com.anusha.nytimes.model.PopularArticles
import com.anusha.nytimes.model.Result
import com.anusha.nytimes.utils.inflateView
import com.anusha.nytimes.utils.views.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_popular_article.view.*


class PopularArticlesListAdapter : BaseAdapter<PopularArticlesListAdapter, FormatedPopularArticle, PopularArticlesListAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = parent.inflateView(R.layout.item_popular_article, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val article = itemList[position]

        holder.itemView.tvAuthor.text = article.byline
        holder.itemView.tvTitle.text = article.title
        holder.itemView.tvPublishDate.text = article.publish_date
        if(article.url.isNotEmpty()){
            Glide.with(holder.itemView.ivArticleImage)
                .load(article.imageUrl)
                .transform(
                    CenterCrop(),
                    RoundedCornersTransformation(55F, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(holder.itemView.ivArticleImage)
        }
        holder.itemView.setOnClickListener { itemClickListener(article) }
    }

    class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
