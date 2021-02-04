package com.something.subfirstjetpack.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.something.subfirstjetpack.data.source.local.LocalDataSource
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.data.source.remote.RemoteDataSource
import com.something.subfirstjetpack.util.AppExecutors
import com.something.subfirstjetpack.util.DataDummy
import com.something.subfirstjetpack.utils.LiveDataTestUtil
import com.something.subfirstjetpack.utils.PagedListUtil
import com.something.subfirstjetpack.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val executors = mock(AppExecutors::class.java)

    private val fakeRepository = FakeMovieRepository(remote, local, executors)

    private val movieLocalResponse = DataDummy.generateDummyMovies()
    private val movieId = movieLocalResponse[0].id.toString()
    private val tvLocalResponse = DataDummy.generateTvShow()
    private val tvId = tvLocalResponse[0].id.toString()

    @Test
    fun getAllMovies() {
        val dataSourceMovie = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceMovie)
        fakeRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(movieLocalResponse))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieLocalResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getAllTvShow() {
        val dataSourceTvShow = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShow()).thenReturn(dataSourceTvShow)
        fakeRepository.getAllTvShow()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(tvLocalResponse))
        verify(local).getAllTvShow()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvLocalResponse.size, tvShowEntities.data?.size)

    }

    @Test
    fun getAllBookmarkMovies(){
        val dataSourceBookmarkMovie = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllBookmarkMovies()).thenReturn(dataSourceBookmarkMovie)
        fakeRepository.getAllBookmarkMovies()

        val movieBookmarkEntities = Resource.success(movieLocalResponse)
        verify(local).getAllBookmarkMovies()
        assertNotNull(movieBookmarkEntities)
        assertEquals(movieLocalResponse.size, movieBookmarkEntities.data?.size)

    }

    @Test
    fun getAllBookmarkTvShow(){

        val dataSourceBookmarkTvShow = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllBookmarkTvShow()).thenReturn(dataSourceBookmarkTvShow)
        fakeRepository.getAllBookmarkTvShow()

        val tvShowBookmarkEntities = Resource.success(tvLocalResponse)
        verify(local).getAllBookmarkTvShow()
        assertNotNull(tvShowBookmarkEntities)
        assertEquals(tvLocalResponse.size, tvShowBookmarkEntities.data?.size)

    }

    @Test
    fun getMovieById(){
        val dummyMovies = MutableLiveData<MovieEntity>()
        dummyMovies.value = DataDummy.generateDummyMovies()[0]
        `when`(local.getMovieById(movieId)).thenReturn(dummyMovies)

        val movieEntities = LiveDataTestUtil.getValue(fakeRepository.getMovieById(movieId))
        verify(local).getMovieById(movieId)
        assertNotNull(movieEntities.data?.name)
        assertNotNull(movieEntities.data?.overview)
        assertNotNull(movieEntities.data?.posterPath)
        assertNotNull(movieEntities.data?.posterPath)
        assertEquals(movieLocalResponse[0].id, movieEntities.data?.id)
    }

    @Test
    fun getTvShowById(){
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = DataDummy.generateTvShow()[0]
        `when`(local.getTvShowById(tvId)).thenReturn(dummyTvShow)

        val tvShowEntities = LiveDataTestUtil.getValue(fakeRepository.getTvShowById(tvId))
        verify(local).getTvShowById(tvId)
        assertNotNull(tvShowEntities.data?.name)
        assertNotNull(tvShowEntities.data?.firstAirDate)
        assertNotNull(tvShowEntities.data?.overview)
        assertNotNull(tvShowEntities.data?.voteAverage)
        assertEquals(tvLocalResponse[0].id, tvShowEntities.data?.id)
    }

}