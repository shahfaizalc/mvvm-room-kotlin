package com.faizal.android.communication

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestHandler {

    /**
     * Class to create Retrofit service
     */
    fun getServiceClubs(baseUrl: String, context: Context): GetServiceClubs = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(RestManager().getCachedRetrofit(context))
            .build()
            .create(GetServiceClubs::class.java)

}