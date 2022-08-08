package com.dk.mvvmnewsapp.repositories

import androidx.lifecycle.LiveData
import com.dk.mvvmnewsapp.network.*
import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Created by Diwesh Singh on 28/07/22.
 */
class ArticlesRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {


    fun getAllArticles(
        articleCategory: String,
        viewModelScope: CoroutineScope
    ): LiveData<ResponseResult<ResponseWrapper<AllArticlesResponse>>> =
        resultLiveData(viewModelScope) {
            getResult {
                apiService.getAllArticles(articleCategory)
            }

        }

}