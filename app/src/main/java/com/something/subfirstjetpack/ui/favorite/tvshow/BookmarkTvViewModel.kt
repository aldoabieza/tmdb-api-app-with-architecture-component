package com.something.subfirstjetpack.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity

class BookmarkTvViewModel(private val repo: MovieRepository): ViewModel() {
    fun getAllTvBookmark(): LiveData<PagedList<TvShowEntity>> = repo.getAllBookmarkTvShow()

    fun setBookmarkTvShow(tvShowEntity: TvShowEntity){
        val state = !tvShowEntity.bookmarked
        repo.addTvShowBookmark(tvShowEntity, state)
    }
}