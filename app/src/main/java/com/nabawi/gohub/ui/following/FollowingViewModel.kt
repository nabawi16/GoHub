package com.nabawi.gohub.ui.following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nabawi.gohub.data.source.repo.Repository

class FollowingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    fun getUserFollowing(username: String) = repository.getUserFollowing(username)
}