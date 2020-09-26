package com.selvakumarsm.flipper.explore.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.ElasticView.StateChangeListener
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
            binding.ivPlace.setImageResource(place.drawableId?: R.drawable.kachiguda)
            binding.root.callback = object : StateChangeListener {
                override fun onCollapsed(view: ElasticView) {
                    binding.tvAbout.text = place.about
                }

                override fun onExpanded(view: ElasticView) {
                    binding.tvAbout.text = place.description
                }
            }
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
}

object PlaceDiffChecker : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean = oldItem == newItem

}