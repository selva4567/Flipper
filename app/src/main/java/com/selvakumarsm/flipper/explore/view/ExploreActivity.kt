package com.selvakumarsm.flipper.explore.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.databinding.ActivityExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        viewModel.progressBarVisibilityLiveData.observe(this) {
            Log.d(TAG, "onCreate: New value for progressbar visibility $it")
            if (it)
                showProgressBar()
            else
                hideProgressBar()
        }

    }

    private fun initFeaturedList() {
        lifecycleScope.launch {
            delay(5000)
            viewBinding.elasticContainer.layoutManager = GridLayoutManager()
            val view = layoutInflater.inflate(R.layout.layout_featured_item, viewBinding.elasticContainer, false)
            viewBinding.elasticContainer.addView(view)
        }
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
}