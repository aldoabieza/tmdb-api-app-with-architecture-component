package com.something.subfirstjetpack.di

import android.content.Context
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.LocalDataSource
import com.something.subfirstjetpack.data.source.local.room.MovieDatabase
import com.something.subfirstjetpack.data.source.remote.RemoteDataSource
import com.something.subfirstjetpack.util.AppExecutors

object Injection {
    fun provideRepository(ctx: Context): MovieRepository {

        val database = MovieDatabase.getInstance(ctx)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()
        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}