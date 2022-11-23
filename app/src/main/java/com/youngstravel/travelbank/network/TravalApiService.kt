package com.youngstravel.travelbank.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.youngstravel.travelbank.data.Expense
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://run.mocky.io/v3/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TravelApiService {
    @GET("178cbbee-c634-4a51-afb8-dcd75c190d29")
    suspend fun getExpenses(): List<Expense>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TravelsApi {
    val retrofitService: TravelApiService by lazy { retrofit.create(TravelApiService::class.java) }
}
