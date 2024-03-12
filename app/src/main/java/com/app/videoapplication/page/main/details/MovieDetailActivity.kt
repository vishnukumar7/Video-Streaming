package com.app.videoapplication.page.main.details

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.app.videoapplication.R
import com.app.videoapplication.databinding.ActivityMovieDetailBinding
import com.app.videoapplication.model.MovieDetailResponse
import com.app.videoapplication.page.BaseActivity
import com.app.videoapplication.page.main.MainApplication
import com.app.videoapplication.utils.AppUtils.toBackdropUrl
import com.app.videoapplication.utils.AppUtils.toOriginalUrl
import com.app.videoapplication.utils.AppViewModelFactory
import com.app.videoapplication.utils.Constants
import com.bumptech.glide.Glide

class MovieDetailActivity : BaseActivity() {

    val TAG="MovieDetailActivity"
    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel : MovieDetailViewModel by viewModels {
        AppViewModelFactory((application as MainApplication).appRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        println("//movie detail : ${intent.getStringExtra(Constants.MOVIE_ID)}")
        if(intent.hasExtra(Constants.MOVIE_ID)){
            viewModel.start(intent.getStringExtra(Constants.MOVIE_ID).toString())
        }

        viewModel.resultsItem.observe(this){
            load(it)
        }
    }

    private fun load(item: MovieDetailResponse){
        Log.e(TAG, "load: ", )
        Glide.with(this).load(item.backdropPath.toBackdropUrl()).into(binding.movieImage)
    }
}