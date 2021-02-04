package com.something.subfirstjetpack.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.vo.Resource

class TvShowViewModel(private val repo: MovieRepository) : ViewModel() {
    fun getTvShow():LiveData<Resource<PagedList<TvShowEntity>>> = repo.getAllTvShow()
}