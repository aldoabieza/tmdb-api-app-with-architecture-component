package com.something.subfirstjetpack.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity

class BookmarkMovieViewModel(private val repo: MovieRepository): ViewModel() {
    fun getAllBookmarkMovies(): LiveData<PagedList<MovieEntity>> = repo.getAllBookmarkMovies()

    fun setBookmarkMovie(movieEntity: MovieEntity){
        val state = !movieEntity.bookmarked
        repo.addMovieBookmark(movieEntity, state)
    }
}