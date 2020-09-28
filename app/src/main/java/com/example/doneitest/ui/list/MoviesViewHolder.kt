package com.example.doneitest.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.doneitest.R
import com.example.doneitest.Utils.Companion.IMG_URL
import com.example.doneitest.data.models.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun initView(movie: Movie?) {
        itemView.tvTitle.text = movie?.originalTitle
        itemView.tvReleaseDate.text = movie?.releaseDate
        Picasso.get().load(IMG_URL + movie?.posterPath)
            .placeholder(R.drawable.poster_placeholder).into(itemView.ivPoster)
    }
}