package com.dk.mvvmnewsapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.dk.mvvmnewsapp.launchFragmentInHiltContainer
import com.dk.mvvmnewsapp.repositories.ArticlesRepository
import com.dk.mvvmnewsapp.viewmodel.AllArticlesViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.dk.mvvmnewsapp.R
import com.dk.mvvmnewsapp.utils.EspressoIdlingResource
import org.junit.After
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


/**
 * Created by Diwesh Singh on 10/08/22.
 */
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class AllArticlesFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        MockitoAnnotations.initMocks(this)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

    }
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }



    @Test
    fun checkRecyclerview_ArticleListLiveDataGetLoaded() {

        val navController = mock(NavController::class.java)

        val allArticlesViewModel = AllArticlesViewModel(
            Mockito.mock(
                ArticlesRepository::class.java
            )
        )

        launchFragmentInHiltContainer<AllArticlesFragment> {
            this.articleViewModel = allArticlesViewModel
        }


        allArticlesViewModel.articleList.observeForever { response ->
            println("list size: " + response.size)
            com.google.common.truth.Truth.assertThat(response.size).isNotEqualTo(0)

            onView(withId(R.id.recycleView_order)).perform(
                ViewActions.scrollTo(),
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()));
        }
    }




    @Test
    fun test_navigateToArticleDetails() {

        val navController = mock(NavController::class.java)

        val allArticlesViewModel = AllArticlesViewModel(
            Mockito.mock(
                ArticlesRepository::class.java
            )
        )

        launchFragmentInHiltContainer<AllArticlesFragment> {
            this.articleViewModel = allArticlesViewModel
        }


        allArticlesViewModel.articleList.observeForever { response ->
            com.google.common.truth.Truth.assertThat(response.size).isNotEqualTo(0)

            verify(navController).navigate(
                AllArticlesFragmentDirections.actionFirstFragmentToSecondFragment(articleUrl = "https://www.google.com")
            )
        }




    }

}