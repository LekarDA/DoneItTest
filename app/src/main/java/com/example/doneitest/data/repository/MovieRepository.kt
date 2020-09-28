package com.example.doneitest.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.doneitest.ApiService
import com.example.doneitest.Utils.Companion.API_KEY
import com.example.doneitest.Utils.Companion.LANGUAGE
import com.example.doneitest.Utils.Companion.POST_PER_PAGE
import com.example.doneitest.data.models.Movie
import com.example.doneitest.data.models.MoviesResponse
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(private val moviesDataSourceFactory: MovieDataSourceFactory){

    lateinit var moviePagedList: LiveData<PagedList<Movie>>

    fun getLiveMoviePagedList(): LiveData<PagedList<Movie>>{
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}