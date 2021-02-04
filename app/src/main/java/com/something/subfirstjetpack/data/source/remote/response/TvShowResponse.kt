package com.something.subfirstjetpack.data.source.remote.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class TvShowResponse(

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val results: List<TvShow>,

    @field:SerializedName("total_results")
	val totalResults: Int
)

data class TvShow(

    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("first_air_date")
    var firstAirDate: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("poster_path")
    var posterPath: String,

    @field:SerializedName("vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false

)

