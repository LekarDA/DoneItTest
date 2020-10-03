package com.example.doneitest.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.doneitest.ApiService
import com.example.doneitest.Utils
import com.example.doneitest.Utils.FIRST_PAGE
import com.example.doneitest.data.models.Movie
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieDataSource @Inject constructor(
    private val apiService: ApiService
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    private var compositeDisposable: CompositeDisposable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable?.add(
            ReactiveNetwork
                .observeInternetConnectivity().subscribe {
                    if (it) {
                        apiService.getPopularMovies(Utils.LANGUAGE, page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {
                                    try {
                                        callback.onResult(it.moviesList, null, page + 1)
                                        networkState.postValue(NetworkState.LOADED)
                                    } catch (e: Exception) {
                                        Log.e("Error", e.message.toString())
                                    }
                                },
                                { errorDuringLoading(it) }
                            )
                    } else  networkState.postValue(NetworkState.DISCONNECT)
                }
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable?.add(
            ReactiveNetwork
                .observeInternetConnectivity()
                .subscribe {
                    if (it) {
                        apiService.getPopularMovies(Utils.LANGUAGE, params.key)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {
                                    if (it.totalPages >= params.key) {
                                        try {
                                            callback.onResult(it.moviesList, params.key + 1)
                                            networkState.postValue(NetworkState.LOADED)
                                        } catch (e: Exception) {
                                            Log.e("Error", e.message.toString())
                                        }

                                    } else {
                                        networkState.postValue(NetworkState.ENDOFLIST)
                                    }
                                },
                                { errorDuringLoading(it) }
                            )
                    } else networkState.postValue(NetworkState.DISCONNECT)
                }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    fun errorDuringLoading(throwable: Throwable) {
        networkState.postValue(NetworkState.ERROR)
        throwable.message?.let { message -> Log.e("MovieDataSource", message) }
    }

    fun setCompositeDisposable(compositeDisposable: CompositeDisposable){
        this.compositeDisposable = compositeDisposable
    }
}