package com.selvakumarsm.flipper.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.home.presentation.RecentStatusAdapter.StatusViewHolder

class RecentStatusAdapter(val callback: ListItemEventCallback) :
    PagingDataAdapter<RecentStatus, StatusViewHolder>(StatusComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        return StatusViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.collapsed_status, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        if (getItem(position) == null)
            return
        holder.bind(getItem(position)!!)
    }

    inner class StatusViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: RecentStatus) {
            view.findViewById<TextView>(R.id.userName).text = status.user
            view.findViewById<ImageView>(R.id.statusImagePeek).setImageResource(R.drawable.scoob)
            view.setOnClickListener {
                callback.onClicked(view)
            }
        }
    }

    companion object {
        const val TAG = "RecentStatusAdapter"
    }

    interface ListItemEventCallback {
        fun onClicked(view: View)
    }
}

object StatusComparator : DiffUtil.ItemCallback<RecentStatus>() {

    override fun areItemsTheSame(oldItem: RecentStatus, newItem: RecentStatus): Boolean {
        return oldItem.user == newItem.user
    }

    override fun areContentsTheSame(oldItem: RecentStatus, newItem: RecentStatus): Boolean {
        return oldItem == newItem
    }

}