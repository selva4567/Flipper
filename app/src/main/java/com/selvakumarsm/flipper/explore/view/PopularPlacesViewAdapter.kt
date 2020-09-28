package com.selvakumarsm.flipper.explore.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.StateChangeListener
import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.databinding.LayoutExploreItemBinding
import com.selvakumarsm.flipper.explore.domain.model.Place

class PopularPlacesViewAdapter :
    ListAdapter<Place, PopularPlacesViewAdapter.PopularPlacesViewHolder>(PlaceDiffChecker) {


    class PopularPlacesViewHolder(private val binding: LayoutExploreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            binding.tvName.text = place.name
            binding.tvMiles.text = place.distance
            binding.ivPlace.setImageResource(place.drawableId ?: R.drawable.kachiguda)
            binding.tvMore.text = place.description
            binding.tvAbout.text = place.about
            binding.root.setStateChangeListener(object : StateChangeListener {
                override fun postCollapse(view: ElasticView) {
                    Log.d(TAG, "postCollapse: ${binding.tvAbout.text}")
                }

                override fun postExpand(view: ElasticView) {
                    Log.d(TAG, "postEXpand: ${binding.tvAbout.text}")
                }

                override fun preCollapse(view: ElasticView) {

                }

                override fun preExpand(view: ElasticView) {
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutExploreItemBinding.inflate(layoutInflater, parent, false)
        return PopularPlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularPlacesViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }

    companion object {
        private val TAG = PopularPlacesViewAdapter::class.simpleName
    }
}

object PlaceDiffChecker : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean = oldItem == newItem

}