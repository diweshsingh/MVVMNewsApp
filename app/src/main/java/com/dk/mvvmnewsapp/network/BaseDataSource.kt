package com.dk.mvvmnewsapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by Diwesh Singh on 7/23/2022.
 */
private const val TAG = "BaseDataSource"

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResponseResult<ResponseWrapper<T>> {
        try {
            val response = call()
            Log.d(TAG, "getResult: $response")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResponseResult.Success(ResponseWrapper(body, null))
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String?): ResponseResult<ResponseWrapper<T>> {
        return ResponseResult.Error(ResponseWrapper(null, message))
    }

}

fun <T> resultLiveData(
    scope: CoroutineScope,
    call: suspend () -> ResponseResult<T>
): LiveData<ResponseResult<T>> {
    return liveData(scope.coroutineContext) {
        emit(ResponseResult.Loading)

        withContext(Dispatchers.IO) {
            emit(call.invoke())
        }
    }
}