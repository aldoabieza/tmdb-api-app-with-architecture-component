package com.something.subfirstjetpack.network

import com.something.subfirstjetpack.data.source.remote.response.MovieResponse
import com.something.subfirstjetpack.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key") apiKey: String) : Call<TvShowResponse>

}