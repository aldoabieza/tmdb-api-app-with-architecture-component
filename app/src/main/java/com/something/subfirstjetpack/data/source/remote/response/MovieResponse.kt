package com.something.subfirstjetpack.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("page")
		val page: Int,

    @field:SerializedName("total_pages")
		val totalPages: Int,

    @field:SerializedName("results")
		val results: List<Movies>,

    @field:SerializedName("total_results")
		val totalResults: Int
)

data class Movies(

    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("poster_path")
    var posterPath: String,

    @field:SerializedName("release_date")
    var firstAirDate: String,

    @field:SerializedName("title")
    var name: String,

    @field:SerializedName("vote_average")
    var voteAverage: Double

)
