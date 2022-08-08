package com.dk.mvvmnewsapp.network


/**
 * Created by Diwesh Singh on 7/23/2022.
 *
 * This class is responsible for managing the response of API in different states.
 */
sealed class ResponseResult<out T> {

    data class Success<T>(val result: T) : ResponseResult<T>()
    data class Error<T>(val error: T) : ResponseResult<T>()
    object Loading : ResponseResult<Nothing>()

}

data class ResponseWrapper<out T>(val data: T?, val errorMessage: String?)