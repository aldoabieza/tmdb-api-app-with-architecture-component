package com.something.subfirstjetpack.data.source.local

import androidx.paging.DataSource
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.data.source.local.room.MovieDAO

class LocalDataSource private constructor(private val dao: MovieDAO){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(dao: MovieDAO): LocalDataSource =
            INSTANCE ?: LocalDataSource(dao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = dao.getMovies()

    fun getAllBookmarkMovies(): DataSource.Factory<Int, MovieEntity> = dao.getBookmarkedMovieFavorite()

    fun getMovieById(movieId: String) = dao.getDetailMovieById(movieId)

    fun insertMovies(listMovies: List<MovieEntity>) = dao.insertMovies(listMovies)

    fun setMovieBookmark(movie: MovieEntity, newState: Boolean){
        movie.bookmarked = newState
        dao.updateMovies(movie)
    }




    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity> = dao.getTvShow()

    fun getAllBookmarkTvShow(): DataSource.Factory<Int, TvShowEntity> = dao.getBookmarkedTvShowFavorite()

    fun getTvShowById(tvId: String) = dao.getDetailTvShowById(tvId)

    fun insertTvShow(listTvShow: List<TvShowEntity>) = dao.insertTvShow(listTvShow)

    fun setTvShowBookmark(tvShow: TvShowEntity, newState: Boolean){
        tvShow.bookmarked = newState
        dao.updateTvShow(tvShow)
    }

}