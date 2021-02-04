package com.something.subfirstjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.vo.Resource

class DetailViewModel(private val repo: MovieRepository) : ViewModel() {

    private val movieId = MutableLiveData<String>()

    private val tvId = MutableLiveData<String>()

    fun selectedMovie(movieId: String){
        this.movieId.value = movieId
    }

    fun selectedTv(tvId: String){
        this.tvId.value = tvId
    }

    var detailMovieById: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId){ mMovieId ->
        repo.getMovieById(mMovieId)
    }

    var detailTvShowById: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(tvId){ mTvShowId ->
        repo.getTvShowById(mTvShowId)
    }

    fun setBookmarkMovie(){
        val movieResource = detailMovieById.value
        if (movieResource != null){
            val movieEntity = movieResource.data
            if (movieEntity != null){
                val newState = !movieEntity.bookmarked
                repo.addMovieBookmark(movieEntity, newState)
            }
        }
    }

    fun setBookmarkTvShow(){
        val tvResource = detailTvShowById.value
        if (tvResource != null){
            val tvEntity = tvResource.data
            if (tvEntity != null){
                val newState = !tvEntity.bookmarked
                repo.addTvShowBookmark(tvEntity, newState)
            }
        }
    }
}