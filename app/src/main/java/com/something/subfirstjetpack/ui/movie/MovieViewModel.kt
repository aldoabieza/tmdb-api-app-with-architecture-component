package com.something.subfirstjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.vo.Resource

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {
    fun getMovies():LiveData<Resource<PagedList<MovieEntity>>> = repo.getAllMovies()
}