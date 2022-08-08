package com.dk.mvvmnewsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.mvvmnewsapp.models.Article
import com.dk.mvvmnewsapp.network.ResponseResult
import com.dk.mvvmnewsapp.network.ResponseWrapper
import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import com.dk.mvvmnewsapp.repositories.ArticlesRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Diwesh Singh on 28/07/22.
 */
@HiltViewModel
class AllArticlesViewModel @Inject constructor(
    private val articlesRemoteDataSource: ArticlesRemoteDataSource
) : ViewModel() {


    // Reference variable for personal profile list
    private var articleList: MutableLiveData<ArrayList<Article>> = MutableLiveData()

    fun getAllArticleFromAPI(articleCategory: String): LiveData<ResponseResult<ResponseWrapper<AllArticlesResponse>>> {
        return articlesRemoteDataSource.getAllArticles(articleCategory, viewModelScope)
    }


    fun getArticleList(): MutableLiveData<ArrayList<Article>> {
        return articleList
    }

    fun setArticleList(articles: List<com.dk.mvvmnewsapp.network.response.Article>) {

        val allArticleList:ArrayList<Article> = ArrayList()
        for (articleItem in articles) {

            val author:String = articleItem.author ?: ""

            val article = Article(
                imageUrl = articleItem.urlToImage,
                title = articleItem.title,
                authorName = author,
                publishedAt = articleItem.publishedAt,
                description = articleItem.description,
                articleUrl = articleItem.url
            )
            allArticleList.add(article)
        }

        articleList.value = allArticleList
    }

}