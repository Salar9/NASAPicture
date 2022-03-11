package com.example.den.nasapicture.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.den.nasapicture.api.NASAApiService
import com.example.den.nasapicture.model.RoverPhotos
import com.example.den.nasapicture.repository.NASAReporitory.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "NASAImagePagingSource"

//наследуемся от PagingSource<Int, List<данные>>
class NASAImagePagingSource(val nasaApiService: NASAApiService): PagingSource<Int, RoverPhotos.Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RoverPhotos.Photo> {
        //при вермом запуске params.key ==NULL поэтому ставим DEFAULT_PAGE_INDEX == 1
        val page = params.key ?: DEFAULT_PAGE_INDEX

        return try {    //тут я возвращаю скачаную страницу и номера следующей и предыдещей страницы
            Log.i(TAG,"Page is $page\tPage size is ${params.loadSize}")
            val response = nasaApiService.getNASAImages(params.loadSize, page)
            LoadResult.Page(
                data = response.photos,    //тут мне нужно вернуть List<Photo>
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1, //если достигли начала то вернем null иначе предыдущую страницу
                nextKey = if (response.photos.isEmpty()) null else page + 1 //если ответ не пустой значит есть ещё страницы, возвращаем ее, и null если конец списка
            )
        } catch (exception: IOException) {
            Log.i(TAG, "IO Exception - ${exception.toString()}")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i(TAG, "Http Exception - ${exception.toString()}")
            return LoadResult.Error(exception)
        }
    }
    // не совсем понятно как это работает
    // взято с https://developer.android.com/topic/libraries/architecture/paging/v3-migration
    override fun getRefreshKey(state: PagingState<Int, RoverPhotos.Photo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}