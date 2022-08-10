package com.dk.mvvmnewsapp.network

import com.dk.mvvmnewsapp.BuildConfig
import com.dk.mvvmnewsapp.repositories.ArticlesRemoteDataSource
import com.dk.mvvmnewsapp.repositories.ArticlesRepository
import com.dk.mvvmnewsapp.utils.NetworkConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Diwesh Singh on 7/23/2022.
 *
 * This class is responsible for handling all the retrofit configuration.
 */
@InstallIn(SingletonComponent::class)
// @Module informs Dagger that this class is a Dagger Module
@Module
class NetworkModule {

    companion object {
        const val DEFAULT_TIMEOUT: Long = 60
        private const val TAG: String = "AppModule"
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getOkHttpClient(
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(logging)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun getAuthenticationInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor()
    }


    @Singleton
    @Provides
    fun provideDefaultArticleRepository(
        api: ApiService
    ) = ArticlesRemoteDataSource(api) as ArticlesRepository


}