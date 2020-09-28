package com.example.doneitest.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.doneitest.data.repository.NetworkState
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bind(networkState: NetworkState?) {
        if (networkState != null && networkState == NetworkState.LOADING) {
            itemView.progress_bar_item.visibility = View.VISIBLE
        }
        else  {
            itemView.progress_bar_item.visibility = View.GONE
        }

        if (networkState != null && networkState == NetworkState.DISCONNECT) {
            itemView.error_msg_item.visibility = View.VISIBLE
            itemView.error_msg_item.text = networkState.msg
        }
        else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
            itemView.error_msg_item.visibility = View.VISIBLE
            itemView.error_msg_item.text = networkState.msg
        }
        else {
            itemView.error_msg_item.visibility = View.GONE
        }
    }
}