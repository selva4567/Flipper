package com.selvakumarsm.flipper.explore.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.selvakumarsm.elasticmodule2.ElasticProperties
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.ElasticStateChangeListener
import com.selvakumarsm.flipper.databinding.ActivityExploreBinding
import com.selvakumarsm.flipper.databinding.LayoutFeaturedItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityExploreBinding
    private val popularPlacesViewAdapter: PopularPlacesViewAdapter = PopularPlacesViewAdapter()
    private val viewModel by viewModels<ExploreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initPopularPlaceView()
        initFeaturedList()

        viewModel.popularPlacesLiveData.observe(this) {
            Log.d(TAG, "onCreate: New value for popular places ${it?.size}")
            popularPlacesViewAdapter.submitList(it)
        }

        viewModel.featuredPlacesLiveData.observe(this){
            Log.d(TAG, "onCreate: New value for featured places ${it?.size}")
            it.forEach {
                // TODO - Replace the views with Fragments to avoid spaghetti code and for better code handling and implement [ElasticProperty] to observe view state (collpased/expanded)
                val featureViewBinding = LayoutFeaturedItemBinding.inflate(layoutInflater, viewBinding.elasticContainer, false)
                featureViewBinding.tvTitle.text = it.title
                featureViewBinding.tvAbout.text = it.about
                featureViewBinding.tvPlaces.text = "${it.noOfPlaces} places"
                featureViewBinding.tvViews.text = "${it.views}K views"
                featureViewBinding.featuredImage.setImageResource(it.resourceId!!)
                viewBinding.elasticContainer.addView(featureViewBinding.root)
                (featureViewBinding.root as ElasticProperties).setStateChangeListener(FeaturedItemElasticStateChangeListener())
            }
        }

        viewModel.progressBarVisibilityLiveData.observe(this) {
            Log.d(TAG, "onCreate: New value for progressbar visibility $it")
            if (it)
                showProgressBar()
            else
                hideProgressBar()
        }

    }

    private fun initFeaturedList() {
        viewBinding.elasticContainer.layoutManager = HorizontalLayoutManager()
    }

    private fun initPopularPlaceView() {
        viewBinding.rvPopularPlaces.apply {
            adapter = popularPlacesViewAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun showProgressBar() {
        viewBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        viewBinding.progressBar.visibility = View.GONE
    }

    companion object {
        private val TAG = ExploreActivity::class.simpleName
    }

    inner class FeaturedItemElasticStateChangeListener: ElasticStateChangeListener {
        override fun postCollapse(view: ElasticView) {
            Log.d(TAG, "postCollapse: ${view.id}")
            view.elevation = 0f
            viewBinding.rvPopularPlaces.visibility = View.VISIBLE
        }

        override fun postExpand(view: ElasticView) {
            Log.d(TAG, "postExpand: ")
        }

        override fun preCollapse(view: ElasticView) {
            Log.d(TAG, "preCollapse: ")
        }

        override fun preExpand(view: ElasticView) {
            Log.d(TAG, "preExpand: ${view.id}")
            view.elevation = 50f
            viewBinding.rvPopularPlaces.visibility = View.INVISIBLE
        }

    }
}

