package com.example.doneitest.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.doneitest.data.repository.NetworkState
import kotlinx.android.synthetic.main.network_state_item.view.*

class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bind(networkState: NetworkState?) {
        if (networkState != null && networkState == NetworkState.LOADING) {
            itemView.progressBarItem.visibility = View.VISIBLE
        }
        else  {
            itemView.progressBarItem.visibility = View.GONE
        }

        if (networkState != null && networkState == NetworkState.DISCONNECT) {
            itemView.errorMsgItem.visibility = View.VISIBLE
            itemView.errorMsgItem.text = networkState.msg
        }
        else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
            itemView.errorMsgItem.visibility = View.VISIBLE
            itemView.errorMsgItem.text = networkState.msg
        }
        else {
            itemView.errorMsgItem.visibility = View.GONE
        }
    }
}