package com.example.music

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Apiinterface {

    @Headers("x-rapidapi-key:cc18b0afa2msh88db1f0dc28c464p142a69jsn8af5ce29b4c7","x-rapidapi-host:deezerdevs-deezer.p.rapidapi.com","Content-Type:application/json")
    @GET("search")
    fun getData(@Query("q") query: String): Call<Mydata>
}