package com.example.doneitest.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.doneitest.data.models.Movie
import com.example.doneitest.data.repository.MovieDataSource
import com.example.doneitest.data.repository.MovieRepository
import com.example.doneitest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesViewModel @ViewModelInject constructor(private val repository : MovieRepository):
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
        repository.setCompositeDisposable(compositeDisposable)
    }

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        repository.getLiveMoviePagedList()
    }

    val  networkState : LiveData<NetworkState> by lazy {
        repository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}