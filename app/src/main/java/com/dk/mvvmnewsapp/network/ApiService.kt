package com.dk.mvvmnewsapp.network

import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import com.dk.mvvmnewsapp.utils.NetworkConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Diwesh Singh on 2/23/2022.
 *
 */
interface ApiService {

    @GET(NetworkConstant.ALL_NEWS_API_END_POINT)
    suspend fun getAllArticles(@Query("q") query:String): Response<AllArticlesResponse>


}