package com.something.subfirstjetpack.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentities where bookmarked = 1")
    fun getBookmarkedMovieFavorite(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies : List<MovieEntity>)

    @Update
    fun updateMovies(movies: MovieEntity)

    @Transaction
    @Query("SELECT * FROM movieentities WHERE movieId = :moviesId")
    fun getDetailMovieById(moviesId: String): LiveData<MovieEntity>

    
    
    

    @Query("SELECT * FROM tventities")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tventities where bookmarked = 1")
    fun getBookmarkedTvShowFavorite(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Transaction
    @Query("SELECT * FROM tventities WHERE tvshowId = :tvId")
    fun getDetailTvShowById(tvId: String): LiveData<TvShowEntity>

}