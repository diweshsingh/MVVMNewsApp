package com.dk.mvvmnewsapp.utils

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showSimpleDialog(
    title: String,
    message: String,
    positiveButton: ActionableButton? = null,
    negativeButton: ActionableButton? = null,
) {
    MaterialAlertDialogBuilder(requireActivity()).apply {
        setTitle(title)
        setMessage(message)
        positiveButton?.let {
            setPositiveButton(it.text) { _, _ -> it.onClick?.invoke() }
        }
        negativeButton?.let {
            setNegativeButton(it.text) { _, _ -> it.onClick?.invoke() }
        }
        show()
    }
}