package com.nabawi.gohub.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nabawi.gohub.data.source.repo.Repository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)
}