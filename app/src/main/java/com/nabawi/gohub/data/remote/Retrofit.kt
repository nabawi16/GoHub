package com.nabawi.gohub.data.remote

import com.nabawi.gohub.utils.Constans.BASE_URL
import com.nabawi.gohub.utils.Constans.GITHUB_TOKEN
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Retrofit {
    private fun client(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", GITHUB_TOKEN)
                val request = requestBuilder.build()
                it.proceed(request)
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    fun create(): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Api::class.java)
}