package com.dk.mvvmnewsapp.network

import com.dk.mvvmnewsapp.utils.NetworkConstant
import okhttp3.Interceptor
import okhttp3.Response


class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()


        val request = original.newBuilder()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + NetworkConstant.API_KEY)
            .header("Content-Type", "application/json")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}