package com.something.subfirstjetpack.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tventities")
data class TvShowEntity(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "tvshowId")
    var id: Int,

    @ColumnInfo(name = "release_date")
    var firstAirDate: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false
)
