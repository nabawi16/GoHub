package com.nabawi.gohub.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabawi.gohub.R
import com.nabawi.gohub.data.model.UserEntity
import com.nabawi.gohub.data.source.Resource
import com.nabawi.gohub.databinding.ActivityMainBinding
import com.nabawi.gohub.ui.adapters.UserAdapter
import com.nabawi.gohub.utils.StateCallback


class MainActivity : AppCompatActivity(), StateCallback<List<UserEntity>> {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userQuery: String
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        userAdapter = UserAdapter()
        mainBinding.includeSearch.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        mainBinding.viewSearch.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    viewModel.searchUser(userQuery).observe(this@MainActivity, {
                        when (it) {
                            is Resource.Error -> onFailed(it.message)
                            is Resource.Loading -> onLoading()
                            is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                        }
                    })
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.m_grid -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            R.id.m_favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.m_setting -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    override fun onSuccess(data: List<UserEntity>) {
        userAdapter.setAllData(data)
        mainBinding.includeSearch.apply {
            ivSearchIcon.visibility = invisible
            tvResponPesan.visibility = invisible
            mainProgressbar.visibility = invisible
            rvListUser.visibility = visible
        }
    }

    override fun onLoading() {
        mainBinding.includeSearch.apply {
            ivSearchIcon.visibility = invisible
            tvResponPesan.visibility = invisible
            mainProgressbar.visibility = visible
            rvListUser.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        mainBinding.includeSearch.apply {
            if (message == null) {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_empty)
                    visibility = visible
                }
                tvResponPesan.apply {
                    text = resources.getString(R.string.user_tidak_ditemukan)
                    visibility = visible
                }
            } else {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_gagal)
                    visibility = visible
                }
                tvResponPesan.apply {
                    text = message
                    visibility = visible
                }
            }
            mainProgressbar .visibility = invisible
            rvListUser.visibility = invisible
        }
    }
}