package com.nabawi.gohub.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nabawi.gohub.data.Api
import com.nabawi.gohub.data.Dao
import com.nabawi.gohub.data.Retrofit
import com.nabawi.gohub.data.UserDatabase
import com.nabawi.gohub.model.SearchResponse
import com.nabawi.gohub.model.UserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val application: Application) {
    private val retrofit: Api
    private val dao: Dao
    private val dataStore: UserDataStore

    init {
        retrofit = Retrofit.create()
        val database: UserDatabase = UserDatabase.getInstance(application)
        dao = database.Dao()
        dataStore = UserDataStore.getInstance(application)
    }

    fun searchUser(query: String): LiveData<Resource<List<UserEntity>>> {
        val listUser = MutableLiveData<Resource<List<UserEntity>>>()

        listUser.postValue(Resource.Loading())
        retrofit.searchUsers(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }

        })
        return listUser
    }

    suspend fun getDetailUser(username: String): LiveData<Resource<UserEntity>> {

        val user = MutableLiveData<Resource<UserEntity>>()

        if (dao.getFavoriteDetailUser(username) != null) {
            //Access Database
            user.postValue(Resource.Success(dao.getFavoriteDetailUser(username)))
        } else {
            //Request ke API
            retrofit.getDetailUser(username).enqueue(object : Callback<UserEntity> {
                override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                    val result = response.body()
                    user.postValue(Resource.Success(result))
                }

                override fun onFailure(call: Call<UserEntity>, t: Throwable) {

                }
            })
        }

        return user
    }

    fun getUserFollowing(username: String): LiveData<Resource<List<UserEntity>>> {

        val listUser = MutableLiveData<Resource<List<UserEntity>>>()

        listUser.postValue(Resource.Loading())
        retrofit.getUserFollowing(username).enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(call: Call<List<UserEntity>>, response: Response<List<UserEntity>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }
        })
        return listUser
    }

    fun getUserFollowers(username: String): LiveData<Resource<List<UserEntity>>> {

        val listUser = MutableLiveData<Resource<List<UserEntity>>>()

        listUser.postValue(Resource.Loading())
        retrofit.getUserFollowers(username).enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(call: Call<List<UserEntity>>, response: Response<List<UserEntity>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }
        })

        return listUser
    }

    suspend fun getFavoriteList(): LiveData<Resource<List<UserEntity>>> {

        val listFavorite = MutableLiveData<Resource<List<UserEntity>>>()
        listFavorite.postValue(Resource.Loading())

        if (dao.getFavoriteListUser().isNullOrEmpty())
            listFavorite.postValue(Resource.Error(null))
        else
            listFavorite.postValue(Resource.Success(dao.getFavoriteListUser()))

        return listFavorite
    }

    suspend fun insertFavoriteUser(user: UserEntity) = dao.insertFavoriteUser(user)

    suspend fun deleteFavoriteUser(user: UserEntity) = dao.deleteFavoriteUser(user)

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) = dataStore.saveThemeSetting(isDarkModeActive)

    fun getThemeSetting() = dataStore.getThemeSetting()
}