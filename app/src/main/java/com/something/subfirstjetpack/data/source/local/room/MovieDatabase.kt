package com.something.subfirstjetpack.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity


@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder( context,
                    MovieDatabase::class.java,
                    "db_movie.db"
                ).build()
            }
    }

}