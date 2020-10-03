package com.example.doneitest.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.doneitest.data.models.Movie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDataSourceFactory @Inject constructor(
    private val movieDataSource: MovieDataSource
): DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

    fun setCompositeDisposable(compositeDisposable: CompositeDisposable) {
        movieDataSource.setCompositeDisposable(compositeDisposable)
    }
}