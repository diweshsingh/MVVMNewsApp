package com.dk.mvvmnewsapp.repositories

import androidx.lifecycle.LiveData
import com.dk.mvvmnewsapp.network.ResponseResult
import com.dk.mvvmnewsapp.network.ResponseWrapper
import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Diwesh Singh on 09/08/22.
 */
interface ArticlesRepository {

     fun getArticlesForCategory(
        articleCategory: String,
        viewModelScope: CoroutineScope
    ): LiveData<ResponseResult<ResponseWrapper<AllArticlesResponse>>>
}