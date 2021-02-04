package com.something.subfirstjetpack.data.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.something.subfirstjetpack.BuildConfig
import com.something.subfirstjetpack.data.source.remote.response.MovieResponse
import com.something.subfirstjetpack.data.source.remote.response.Movies
import com.something.subfirstjetpack.data.source.remote.response.TvShow
import com.something.subfirstjetpack.data.source.remote.response.TvShowResponse
import com.something.subfirstjetpack.network.ApiConfig
import com.something.subfirstjetpack.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource{

    private val apiKey = BuildConfig.MovieToken
    private var handler = Handler(Looper.getMainLooper())

    companion object{
        private const val TIME_LIMIT: Long = 2000
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
                instance ?: synchronized(this){
                    instance ?: RemoteDataSource()
                }
    }

    fun getMovies(): LiveData<ApiResponse<List<Movies>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<Movies>>>()
        handler.postDelayed({
            ApiConfig.getApiService().getMovies(apiKey)
                    .enqueue(object : Callback<MovieResponse>{
                        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                            if (response.isSuccessful){
                                resultMovies.postValue(response.body()?.let { ApiResponse.success(it.results) })
                                EspressoIdlingResource.decrement()
                            }
                        }

                        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                            Log.d("OnFailure", t.message.toString())
                        }
                    })
        }, TIME_LIMIT)
        return resultMovies
    }



    fun getTvShow(): LiveData<ApiResponse<List<TvShow>>>{
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShow>>>()
        handler.postDelayed({
            ApiConfig.getApiService().getTvShows(apiKey)
                    .enqueue(object : Callback<TvShowResponse>{
                        override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                            if (response.isSuccessful){
                                resultTvShow.postValue(response.body()?.let { ApiResponse.success(it.results) })
                                EspressoIdlingResource.decrement()
                            }
                        }

                        override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                            Log.d("OnFailure", t.message.toString())
                        }

                    })
        }, TIME_LIMIT)
        return resultTvShow
    }

}