package com.example.den.nasapicture.repository

import androidx.paging.*
import com.example.den.nasapicture.api.NASAApiService
import com.example.den.nasapicture.api.NASAInterceptor
import com.example.den.nasapicture.data.NASAImagePagingSource
import com.example.den.nasapicture.model.RoverPhotos
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NASAReporitory {
    private val nasaApi: NASAApiService
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        //get doggo repository instance
        fun getInstance() = NASAReporitory()
    }

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(getHttpLogger())
            .addInterceptor(NASAInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        nasaApi = retrofit.create(NASAApiService::class.java)
    }
    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun letNASAImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<RoverPhotos.Photo>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { NASAImagePagingSource(nasaApi) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)    //PagingConfig часть paging3_Lib
    }
    //suspend fun test() = nasaApi.getNASAImages(25,1)
    //suspend fun test() = nasaApi.getNASAImages()
}