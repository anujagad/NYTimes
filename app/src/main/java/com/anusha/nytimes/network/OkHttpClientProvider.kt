package com.algorythma.retail.network


import com.anusha.nytimes.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object OkHttpClientProvider {

    fun get(): OkHttpClient = client
    const val HEADER_AUTHORIZATION = "Authorization"

    private val client by lazy {

        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
               /* .addInterceptor {
                    var request = it.request()
                    if (!request.url().url().path.contains("/auth/")) {
                        request = request.newBuilder().addHeader(
                                HEADER_AUTHORIZATION, App.prefs.accessToken
                                ?: ""
                        )
                                .url(request.url())
                                .build()
                    }
                    request = request.newBuilder().addHeader(
                            HEADER_DEVICE_ID, App.prefs.deviceId
                            ?: throw RuntimeException("DeviceId must not be null")
                    )
                            .url(request.url())
                            .build()
                    it.proceed(request)
                }*/
                .addInterceptor(logging)
                .build()
    }
}