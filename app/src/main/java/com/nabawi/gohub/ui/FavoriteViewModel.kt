package com.nabawi.gohub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nabawi.gohub.data.source.repo.Repository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)
    suspend fun getFavoriteList() = repository.getFavoriteList()
}