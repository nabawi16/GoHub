package com.nabawi.gohub.utils

import android.view.View

interface StateCallback {
    fun onSuccess(data: T)
    fun onLoading()
    fun onFailed(message: String?)

    val invisible: Int
        get() = View.INVISIBLE

    val visible: Int
        get() = View.VISIBLE
}