package com.example.doneitest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doneitest.R
import com.example.doneitest.Utils.MOVIE_VIEW_TYPE
import com.example.doneitest.data.repository.NetworkState
import com.example.doneitest.ui.list.MoviesPagedListAdapter
import com.example.doneitest.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter : MoviesPagedListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createList()

        viewModel.moviePagedList.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            pbLoading.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            tvMessage.visibility = if (viewModel.listIsEmpty() && it == NetworkState.DISCONNECT) View.VISIBLE else View.GONE
            if (it == NetworkState.ERROR)
            Toast.makeText(this,resources.getString(R.string.server_error),Toast.LENGTH_LONG).show()

            if (!viewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun createList() {
        adapter = MoviesPagedListAdapter()
        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                if (viewType == MOVIE_VIEW_TYPE) return  1
                else return 3
            }
        }
        rvListOfMovies.layoutManager = gridLayoutManager
        rvListOfMovies.setHasFixedSize(true)
        rvListOfMovies.adapter = adapter
    }
}