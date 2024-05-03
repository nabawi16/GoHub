package com.nabawi.gohub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nabawi.gohub.data.model.UserEntity

@Database(entities = [UserEntity::class], exportSchema = false, version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun Dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "User.db"
                    ).build()
                }
            }
            return INSTANCE as UserDatabase
        }
    }
}