package com.anusha.nytimes.network

import com.algorythma.retail.network.GsonProvider
import com.algorythma.retail.network.OkHttpClientProvider
import com.anusha.nytimes.BuildConfig
import com.anusha.nytimes.model.PopularArticles
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object NYTimesRestAPI {
    private val BASE_URL = BuildConfig.BASEURL
    private val okHttpClient = OkHttpClientProvider.get()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonProvider.get())).build()

    @JvmSuppressWildcards
    interface RestAPI {
        @GET("mostviewed/{section}/{period}.json")
        suspend fun getNYTimesMostPopularArticles(
            @Path("section") section: String,
            @Path("period") period: Int,
            @Query("api-key") apiKey: String
        ) : Response<PopularArticles>

    }

    val serviceClient = retrofit.create(RestAPI::class.java)
}