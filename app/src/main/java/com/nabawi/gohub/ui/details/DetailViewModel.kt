package com.nabawi.gohub.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nabawi.gohub.data.model.UserEntity
import com.nabawi.gohub.data.source.repo.Repository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val repository = Repository(application)

    suspend fun getDetailUser(username: String) = repository.getDetailUser(username)

    fun insertFavoriteUser(user: UserEntity) = viewModelScope.launch {
        repository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: UserEntity) = viewModelScope.launch {
        repository.deleteFavoriteUser(user)
    }
}