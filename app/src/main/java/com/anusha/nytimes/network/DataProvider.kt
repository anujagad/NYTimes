package com.anusha.nytimes.network

import android.content.Context
import com.algorythma.retail.network.makeRequest

object DataProvider {

    var unauthorizedCallback: () -> Unit = {}

    var getContext: () -> Context? = { null }

    suspend fun getPopularArticles(section: String ,period: Int,apiKey: String) = makeRequest {
        NYTimesRestAPI.serviceClient.getNYTimesMostPopularArticles(section,period,apiKey)
    }
}