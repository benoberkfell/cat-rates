package com.example.catrates.catapi

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/api/images/get")
    fun fetchCatPictures(@Query("format") format: String = "xml",
                         @Query("results_per_page") count: Number = 20) : Single<ResponseBody>

}