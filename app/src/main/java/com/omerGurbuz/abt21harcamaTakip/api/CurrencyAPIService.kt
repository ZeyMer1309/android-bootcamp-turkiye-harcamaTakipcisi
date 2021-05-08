package com.omerGurbuz.abt21harcamaTakip.api

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyAPIService {
    private val BASE_URL = "http://api.exchangeratesapi.io/v1/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(CurrencyAPI::class.java)

    fun loadData(): Single<kurDegerleriModeli> {
        return api.getData()
    }
}