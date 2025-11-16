package com.example.project1.retrofit

import com.example.project1.model.ProductResponse
import retrofit2.http.GET

interface ApiServices {
    @GET("products")
    suspend fun getProduct(): ProductResponse
}