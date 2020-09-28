package com.example.doneitest

import com.example.doneitest.data.models.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") key: Int
    ): Single<MoviesResponse>
}