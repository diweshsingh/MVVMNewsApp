package com.dk.mvvmnewsapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dk.mvvmnewsapp.network.ResponseResult
import com.dk.mvvmnewsapp.network.ResponseWrapper
import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import kotlinx.coroutines.CoroutineScope


class FakeAllArticlesRepository : ArticlesRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }


    override fun getArticlesForCategory(
        articleCategory: String,
        viewModelScope: CoroutineScope
    ): LiveData<ResponseResult<ResponseWrapper<AllArticlesResponse>>> {

//        return if(shouldReturnNetworkError) {
//            ResultWrapper.GenericError(503, null)
//        } else {
//            ResultWrapper.Success(ProductListResponse(emptyList()))

        val result: LiveData<ResponseResult<ResponseWrapper<AllArticlesResponse>>> =
            MutableLiveData()
        return result
//        }
    }

}