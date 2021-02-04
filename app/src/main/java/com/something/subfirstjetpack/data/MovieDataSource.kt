package com.something.subfirstjetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.vo.Resource

interface MovieDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getAllBookmarkMovies(): LiveData<PagedList<MovieEntity>>
    fun getAllBookmarkTvShow(): LiveData<PagedList<TvShowEntity>>
    fun getMovieById(movieId: String): LiveData<Resource<MovieEntity>>
    fun getTvShowById(tvId: String): LiveData<Resource<TvShowEntity>>
    fun addMovieBookmark(movies: MovieEntity, state: Boolean)
    fun addTvShowBookmark(tvShow: TvShowEntity, state: Boolean)
}