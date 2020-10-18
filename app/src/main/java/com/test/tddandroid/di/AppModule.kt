package com.test.tddandroid.di

import android.content.Context
import com.google.gson.Gson
import com.test.tddandroid.database.ShoppingDB
import com.test.tddandroid.net.configs.PixelBayApi
import com.test.tddandroid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesPixelBayApi(retrofit: Retrofit): PixelBayApi =
        retrofit.create(PixelBayApi::class.java)

    @Singleton
    @Provides
    fun providesDb(@ApplicationContext context: Context): ShoppingDB {
        return ShoppingDB.getDataBase(context = context)
    }

    @Singleton
    @Provides
    fun providesShoppingDao(db: ShoppingDB) = db.shopppingDao()

    @Singleton
    @Provides
    fun providesRetrofit() :Retrofit{

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()

        client.addInterceptor(loggingInterceptor)
      return  Retrofit.Builder()
            .client(client.build())
            .addConverterFactory(
                GsonConverterFactory.create(
                    Gson()
                )
            ).baseUrl(Constants.BASE_URL).build()
    }

}