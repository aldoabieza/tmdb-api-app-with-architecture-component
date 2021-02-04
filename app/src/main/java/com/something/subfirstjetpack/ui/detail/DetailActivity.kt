package com.something.subfirstjetpack.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.something.subfirstjetpack.R
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.databinding.ActivityDetailBinding
import com.something.subfirstjetpack.viewmodel.ViewModelFactory
import com.something.subfirstjetpack.vo.Status

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private var menu: Menu? = null

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV = "extra_tv"
        private const val IMG_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val movieId = intent.getStringExtra(EXTRA_MOVIE)
        val tvId = intent.getStringExtra(EXTRA_TV)

        when{
            movieId != null -> {
                viewModel.selectedMovie(movieId)
                viewModel.detailMovieById.observe(this, { detailMovie ->
                    if (detailMovie != null){
                        when(detailMovie.status){
                            Status.LOADING -> binding.pbDetail.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.pbDetail.visibility = View.GONE
                                detailMovie.data?.let { setDataMovie(it) }
                            }
                            Status.ERROR -> {
                                binding.pbDetail.visibility = View.GONE
                                Toast.makeText(applicationContext, "Not Connected", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
            tvId != null -> {
                viewModel.selectedTv(tvId)
                viewModel.detailTvShowById.observe(this, { detailTvShow ->
                    if (detailTvShow != null){
                        when(detailTvShow.status){
                            Status.LOADING -> binding.pbDetail.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.pbDetail.visibility = View.GONE
                                detailTvShow.data?.let { setDataTvShow(it) }
                            }
                            Status.ERROR -> {
                                binding.pbDetail.visibility = View.GONE
                                Toast.makeText(applicationContext, "Not Connected", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu

        when{
            intent.getStringExtra(EXTRA_MOVIE) != null -> {
                viewModel.detailMovieById.observe(this, { detailMovie ->
                    if (detailMovie != null){
                        when(detailMovie.status){
                            Status.LOADING -> binding.pbDetail.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.pbDetail.visibility = View.GONE
                                val state = detailMovie.data?.bookmarked
                                state?.let { setButtonFavorite(it) }
                            }
                            Status.ERROR -> {
                                binding.pbDetail.visibility = View.GONE
                                Toast.makeText(applicationContext, "Not Connected", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
            intent.extras?.getString(EXTRA_TV) != null -> {
                viewModel.detailTvShowById.observe(this, { detailTvShow ->
                    if (detailTvShow != null){
                        when(detailTvShow.status){
                            Status.LOADING -> binding.pbDetail.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.pbDetail.visibility = View.GONE
                                val state = detailTvShow.data?.bookmarked
                                state?.let { setButtonFavorite(it) }
                            }
                            Status.ERROR -> {
                                binding.pbDetail.visibility = View.GONE
                                Toast.makeText(applicationContext, "Not Connected", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            when{
                intent.getStringExtra(EXTRA_MOVIE) != null -> {
                    viewModel.setBookmarkMovie()
                    return true
                }
                intent.getStringExtra(EXTRA_TV) != null -> {
                    viewModel.setBookmarkTvShow()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setButtonFavorite(state: Boolean){
        if (menu == null) return
        val menuItems = menu?.findItem(R.id.action_bookmark)
        if (state){
            menuItems?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_full_border)
        }else{
            menuItems?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
        }
    }

    private fun setDataMovie(listMovie: MovieEntity) {
        binding.apply {
            detailTitle.text = listMovie.name
            detailOverview.text = listMovie.overview
            detailRelease.text = listMovie.firstAirDate
            detailVote.text = listMovie.voteAverage.toString()
        }
        supportActionBar?.title = listMovie.name
        loadImage("${IMG_URL}${listMovie.posterPath}", binding.detailImg)
    }

    private fun setDataTvShow(listTv: TvShowEntity) {
        binding.apply {
            detailTitle.text = listTv.name
            detailVote.text = listTv.voteAverage.toString()
            detailRelease.text = listTv.firstAirDate
            detailOverview.text = listTv.overview
        }
        supportActionBar?.title = listTv.name
        loadImage("${IMG_URL}${listTv.posterPath}", binding.detailImg)
    }

    private fun loadImage(url: String, image: ImageView){
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_baseline_replay_24).error(R.drawable.ic_baseline_error_24)
                .into(image)
    }
}