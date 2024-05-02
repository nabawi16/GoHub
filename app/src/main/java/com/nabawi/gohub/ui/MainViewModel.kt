package com.nabawi.gohub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)
}