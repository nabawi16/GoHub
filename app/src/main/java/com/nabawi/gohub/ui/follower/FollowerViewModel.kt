package com.nabawi.gohub.ui.follower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nabawi.gohub.data.source.repo.Repository

class FollowerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    fun getUserFollowers(username: String) = repository.getUserFollowers(username)
}