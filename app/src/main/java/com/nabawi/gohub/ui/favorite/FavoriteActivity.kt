package com.nabawi.gohub.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabawi.gohub.data.model.UserEntity
import com.nabawi.gohub.data.source.Resource
import com.nabawi.gohub.databinding.ActivityFavoriteBinding
import com.nabawi.gohub.ui.adapters.UserAdapter
import com.nabawi.gohub.utils.StateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity: AppCompatActivity(), StateCallback<List<UserEntity>> {
    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        userAdapter = UserAdapter()

        favoriteBinding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity, {
                when(it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity, {
                when(it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            })
        }
    }
    override fun onSuccess(data: List<UserEntity>) {
        favoriteBinding.apply {
            progressbarFavorite.visibility = invisible
        }
        userAdapter.setAllData(data)
    }

    override fun onLoading() {
        favoriteBinding.apply {
            progressbarFavorite.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        if (message == null) {
            favoriteBinding.apply {
                progressbarFavorite.visibility = invisible
                tvErrorFavorite.text = "Tidak ada pengguna di daftar favorit"
            }
        }
    }

}