package com.dk.mvvmnewsapp.listener

import com.dk.mvvmnewsapp.models.Article

/**
 * Created by Diwesh Singh on 01/08/22.
 */
interface OnArticleItemClick {

    fun onArticleItemClick(article: Article)
}