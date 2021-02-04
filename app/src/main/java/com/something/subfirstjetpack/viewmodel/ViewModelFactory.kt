package com.something.subfirstjetpack.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.di.Injection
import com.something.subfirstjetpack.ui.detail.DetailViewModel
import com.something.subfirstjetpack.ui.favorite.movie.BookmarkMovieViewModel
import com.something.subfirstjetpack.ui.favorite.tvshow.BookmarkTvViewModel
import com.something.subfirstjetpack.ui.movie.MovieViewModel
import com.something.subfirstjetpack.ui.tvshow.TvShowViewModel

class ViewModelFactory(private val repository: MovieRepository): ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(ctx: Context): ViewModelFactory =
                instance ?: synchronized(this){
                    instance ?: ViewModelFactory(Injection.provideRepository(ctx))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(repository) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(repository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repository) as T
            modelClass.isAssignableFrom(BookmarkMovieViewModel::class.java) -> BookmarkMovieViewModel(repository) as T
            modelClass.isAssignableFrom(BookmarkTvViewModel::class.java) -> BookmarkTvViewModel(repository) as T
            else -> throw Throwable("ViewModel Not Found : " + modelClass.name)
        }
    }
}