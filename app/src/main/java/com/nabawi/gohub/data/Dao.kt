package com.nabawi.gohub.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nabawi.gohub.model.UserEntity

interface Dao {
    @Query("SELECT * FROM user ORDER BY username ASC")
    suspend fun getFavoriteListUser(): List<UserEntity>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getFavoriteDetailUser(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: UserEntity)

    @Delete
    suspend fun deleteFavoriteUser(user: UserEntity)
}