package com.dk.mvvmnewsapp.utils

data class ActionableButton(val text: String, val onClick: (() -> Unit)? = null)