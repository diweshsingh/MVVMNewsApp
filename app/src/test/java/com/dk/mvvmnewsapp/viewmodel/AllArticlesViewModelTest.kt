package com.dk.mvvmnewsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dk.mvvmnewsapp.models.Article
import com.dk.mvvmnewsapp.network.ResponseResult
import com.dk.mvvmnewsapp.network.ResponseWrapper
import com.dk.mvvmnewsapp.network.response.AllArticlesResponse
import com.dk.mvvmnewsapp.network.response.Source
import com.dk.mvvmnewsapp.repositories.FakeAllArticlesRepository
import com.dk.mvvmnewsapp.utils.MainCoroutineRule
import com.dk.mvvmnewsapp.utils.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Created by Diwesh Singh on 09/08/22.
 */
class AllArticlesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AllArticlesViewModel
    private lateinit var fakeAllArticlesRepository: FakeAllArticlesRepository

    @Before
    fun setup() {
        fakeAllArticlesRepository = FakeAllArticlesRepository()
        viewModel = AllArticlesViewModel(fakeAllArticlesRepository)
    }

    @Test
    fun checkIfGetArticlesForCategory_returnEmptyList() {
        val value = viewModel.getArticlesForCategoryFromAPI("Tech").getOrAwaitValueTest()

        // Setting up data
        val emptySuccessResponse = ResponseResult.Success(
            ResponseWrapper(
                AllArticlesResponse(
                    articles = emptyList(),
                    status = "Success",
                    totalResults = 0
                ), null
            )
        )

        // Assert
        assertThat(value).isEqualTo(emptySuccessResponse)
    }

    @Test
    fun checkIfGetArticlesForCategory_returnErrorWithMessage() {
        //Setting the network error value to True
        fakeAllArticlesRepository.setShouldReturnNetworkError(true)
        val value = viewModel.getArticlesForCategoryFromAPI("Tech").getOrAwaitValueTest()

        // Assert
        assertThat(value).isEqualTo(
            ResponseResult.Error(
                ResponseWrapper(
                    null,
                    "Check your internet connection & Please try again."
                )
            )
        )
    }


    @Test
    fun checkIfCreateArticleList_UpdateEmptyList() {
        // Setting up data
        viewModel.createArticleList(emptyList<com.dk.mvvmnewsapp.network.response.Article>())

        //Get the data
        val value = viewModel.articleList.getOrAwaitValueTest()


        // Assert
        assertThat(value).isEqualTo(emptyList<Article>())
    }

    @Test
    fun checkIfCreateArticleList_UpdateDataList() {
        // Setting up data
        val source = Source(
            id = "1",
            name = "Test article"
        )

        val articleResponse = com.dk.mvvmnewsapp.network.response.Article(
            author = "John",
            description = "This is test data",
            content = "This is test data",
            publishedAt = "10th Feb 2022",
            title = "Test data",
            source = source,
            url = "https://android.devs",
            urlToImage = "https://android.devs.profile.jpg",
        )

        val articleList: ArrayList<com.dk.mvvmnewsapp.network.response.Article> = ArrayList()
        articleList.add(articleResponse)
        viewModel.createArticleList(articleList)

        //Get the data
        val value = viewModel.articleList.getOrAwaitValueTest()


        // Assert

        val article = Article(
            imageUrl = articleResponse.urlToImage,
            articleUrl = articleResponse.url,
            title = articleResponse.title,
            publishedAt = articleResponse.publishedAt,
            description = articleResponse.description,
            authorName = articleResponse.author!!
        )
        // Check if article list has the article data or not
        assertThat(value).contains(article)

        // Check if article size if equal to 1
        assertThat(value.size).isEqualTo(1)
    }

}