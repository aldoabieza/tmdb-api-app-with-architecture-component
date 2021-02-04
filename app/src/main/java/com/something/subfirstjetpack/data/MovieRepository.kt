package com.something.subfirstjetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.something.subfirstjetpack.data.source.local.LocalDataSource
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.data.source.remote.ApiResponse
import com.something.subfirstjetpack.data.source.remote.RemoteDataSource
import com.something.subfirstjetpack.data.source.remote.response.Movies
import com.something.subfirstjetpack.data.source.remote.response.TvShow
import com.something.subfirstjetpack.util.AppExecutors
import com.something.subfirstjetpack.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): MovieDataSource {

    companion object{
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(remote: RemoteDataSource, local: LocalDataSource, executors: AppExecutors): MovieRepository =
                instance ?: synchronized(this){
                    instance ?: MovieRepository(remote, local, executors)
                }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        object : NetworkBoundResource<PagedList<MovieEntity>, List<Movies>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Movies>>> = remoteDataSource.getMovies()

            override fun saveCallResult(data: List<Movies>){
                val movieList = ArrayList<MovieEntity>()
                for (response in data){
                    val movies = MovieEntity(
                        response.id,
                        response.overview,
                        response.posterPath,
                        response.firstAirDate,
                        response.name,
                        response.voteAverage,
                        false
                    )
                    movieList.add(movies)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()


    override fun getAllBookmarkMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()

        return LivePagedListBuilder(localDataSource.getAllBookmarkMovies(), config).build()
    }

    override fun getMovieById(movieId: String): LiveData<Resource<MovieEntity>> =
        object : NetworkBoundResource<MovieEntity, List<Movies>>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<List<Movies>>> = remoteDataSource.getMovies()

            override fun saveCallResult(data: List<Movies>){
                val movieList = ArrayList<MovieEntity>()
                for (response in data){
                    val movies = MovieEntity(
                        response.id,
                        response.overview,
                        response.posterPath,
                        response.firstAirDate,
                        response.name,
                        response.voteAverage,
                        false
                    )
                    movieList.add(movies)
                }
                localDataSource.getMovieById(movieId)
            }
        }.asLiveData()



    override fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> =
        object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShow>>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()

                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> = remoteDataSource.getTvShow()

            override fun saveCallResult(data: List<TvShow>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data){
                    val tvShow = TvShowEntity(
                        response.id,
                        response.firstAirDate,
                        response.name,
                        response.overview,
                        response.posterPath,
                        response.voteAverage,
                        false
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShow(tvShowList)
            }

        }.asLiveData()


    override fun getAllBookmarkTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getAllBookmarkTvShow(), config).build()
    }

    override fun getTvShowById(tvId: String): LiveData<Resource<TvShowEntity>> =
        object : NetworkBoundResource<TvShowEntity, List<TvShow>>(appExecutors){

            override fun loadFromDB(): LiveData<TvShowEntity> = localDataSource.getTvShowById(tvId)

            override fun shouldFetch(data: TvShowEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> = remoteDataSource.getTvShow()

            override fun saveCallResult(data: List<TvShow>) {
                val listTv = ArrayList<TvShowEntity>()
                for (response in data){
                    val tvShow = TvShowEntity(
                        response.id,
                        response.firstAirDate,
                        response.name,
                        response.overview,
                        response.posterPath,
                        response.voteAverage,
                        false
                    )
                    listTv.add(tvShow)
                }
                localDataSource.getTvShowById(tvId)
            }
        }.asLiveData()

    override fun addMovieBookmark(movies: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieBookmark(movies, state) }

    override fun addTvShowBookmark(tvShow: TvShowEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvShowBookmark(tvShow, state) }

}