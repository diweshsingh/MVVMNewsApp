package com.dk.mvvmnewsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.mvvmnewsapp.models.Article
import com.dk.mvvmnewsapp.network.ResponseResult
import com.dk.mvvmnewsapp.network.ResponseWrapper
import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import com.dk.mvvmnewsapp.repositories.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Diwesh Singh on 28/07/22.
 */
@HiltViewModel
class AllArticlesViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : ViewModel() {


    // Reference variable for personal profile list
    private var _articleList: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    var articleList: LiveData<ArrayList<Article>> = _articleList

    /**
     * Method to get data from repository
     */
    fun getArticlesForCategoryFromAPI(articleCategory: String): LiveData<ResponseResult<ResponseWrapper<AllArticlesResponse>>> {
        return articlesRepository.getArticlesForCategory(articleCategory, viewModelScope)
    }


    fun createArticleList(articles: List<com.dk.mvvmnewsapp.network.response.Article>) {

        val allArticleList: ArrayList<Article> = ArrayList()
        for (articleItem in articles) {

            val author: String = articleItem.author ?: ""
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

        _articleList.value = allArticleList
    }

}