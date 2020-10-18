package com.test.tddandroid.net.configs

import com.test.tddandroid.BuildConfig
import com.test.tddandroid.net.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixelBayApi {
    @GET("api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}