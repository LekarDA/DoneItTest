package com.example.doneitest.data.models

import android.os.Parcelable
import com.example.doneitest.data.models.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResponse(
    val page: String,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val moviesList:List<Movie>
) : Parcelable