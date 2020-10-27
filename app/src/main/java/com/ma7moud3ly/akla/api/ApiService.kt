/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.api

import com.ma7moud3ly.akla.App
import com.ma7moud3ly.akla.util.CONSTANTS.Companion.BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File


interface ApiService {

    /**
     * this method send get request to the api/recipes path to fetch some recipes
     * @param page page number to fetch
     * @param size number of recipes per request
     */
    @GET("api/recipes")
    suspend fun getRecipes(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): ApiResponse?

    /**
     * this method send get request to the api/filter path to search for specific recipes
     * @param page page number to fetch
     * @param size number of recipes per request
     * @param name the recipe name to search for
     * @param category the recipe category to search for
     * @param cuisine the recipe cuisine to search for
     */
    @GET("api/filter")
    suspend fun searchRecipes(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("name") name: String? = "",
        @Query("category") category: String? = "",
        @Query("cuisine") cuisine: String? = ""
    ): ApiResponse?

    //retrofit singleton handles the api requestss
    companion object {
        fun create(): ApiService {

            val httpCacheDirectory = File(App.context.getCacheDir(), "responses")
            val cacheSize:Long = 10 * 1024 * 1024 // 10 MiB
            val cache = Cache(httpCacheDirectory, cacheSize)

            val client = OkHttpClient.Builder()
                .addInterceptor(CacheInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }

}


