package com.example.den.nasapicture.api


import com.example.den.nasapicture.model.RoverPhotos
import retrofit2.http.GET
import retrofit2.http.Query

interface NASAApiService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getNASAImages(@Query("sol") size: Int,@Query("page") page: Int): RoverPhotos
}
//"https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=2&page=3&api_key=DEMO_KEY"