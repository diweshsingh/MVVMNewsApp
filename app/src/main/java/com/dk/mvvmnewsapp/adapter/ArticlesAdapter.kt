package com.dk.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dk.mvvmnewsapp.R
import com.dk.mvvmnewsapp.databinding.ItemArticlesBinding
import com.dk.mvvmnewsapp.listener.OnArticleItemClick
import com.dk.mvvmnewsapp.models.Article

/**
 * Created by Diwesh Singh on 30/07/22.
 */
class ArticlesAdapter(
    private var articlesList: List<Article>,
    private val onArticleItemClick: OnArticleItemClick
) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemArticlesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_articles, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: Article = articlesList[position]
        holder.bind(dataModel, onArticleItemClick)
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    class ViewHolder(private val itemRowBinding: ItemArticlesBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {

        fun bind(article: Article, onArticleItemClick: OnArticleItemClick) {
            itemRowBinding.article = article
            Glide.with(itemRowBinding.root.context)
                .load(article.imageUrl)
                .into(itemRowBinding.imageviewArticlesCover)
            itemRowBinding.imageviewArticlesCover
            itemRowBinding.executePendingBindings()

            itemRowBinding.root.setOnClickListener {
                onArticleItemClick.onArticleItemClick(article)
            }
        }

    }

    fun updateData(profileList: List<Article>) {
        this.articlesList = profileList
        notifyDataSetChanged()
    }

}