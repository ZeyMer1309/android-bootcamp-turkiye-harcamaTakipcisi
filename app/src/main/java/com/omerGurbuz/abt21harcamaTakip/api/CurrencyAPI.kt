package com.omerGurbuz.abt21harcamaTakip.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    // http://api.exchangeratesapi.io/v1/latest?access_key=2681086445d9ac3f27a76706d384106c&base=TRY&symbols=USD,EUR,GBP,JPY&format=1

    @GET("latest")
    fun getData(
        @Query("access_key") key: String = "2681086445d9ac3f27a76706d384106c",
        @Query("symbols") symbols: String = "USD,TRY,GBP,JPY,EUR",
        @Query("format") format: String = "1"
    ) : Single<kurDegerleriModeli>

}