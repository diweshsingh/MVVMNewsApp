package com.dk.mvvmnewsapp.utils

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by Diwesh Singh on 14/08/22.
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}