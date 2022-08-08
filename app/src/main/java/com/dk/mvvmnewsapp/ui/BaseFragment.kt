package com.dk.mvvmnewsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData


/**
 * Created by Diwesh Singh on 7/23/2022.
 *
 * This is base fragment class and we can put all the methods here which will be reused in most of fragments class.
 */

abstract class BaseFragment : Fragment() {


    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(viewLifecycleOwner) { data ->
            data?.let(action)
        }
    }
}