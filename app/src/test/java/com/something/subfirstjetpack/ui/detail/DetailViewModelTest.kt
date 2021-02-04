package com.something.subfirstjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.util.DataDummy
import com.something.subfirstjetpack.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val dummyMovies = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovies.id.toString()
    private val dummyTv = DataDummy.generateTvShow()[0]
    private val tvId = dummyTv.id.toString()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp(){
        viewModel = DetailViewModel(movieRepository)
        viewModel.selectedMovie(movieId)
        viewModel.selectedTv(tvId)
    }

    @Test
    fun getDetailMovies() {
        val moviesResource = Resource.success(dummyMovies)
        val dummyMoviesDetail = MutableLiveData<Resource<MovieEntity>>()
        dummyMoviesDetail.value = moviesResource
        `when`(movieRepository.getMovieById(movieId)).thenReturn(dummyMoviesDetail)

        viewModel.detailMovieById.observeForever(movieObserver)
        verify(movieObserver).onChanged(moviesResource)
    }

    @Test
    fun getDetailTvShow() {
        val tvResource = Resource.success(dummyTv)
        val tvShowDetail = MutableLiveData<Resource<TvShowEntity>>()
        tvShowDetail.value = tvResource
        `when`(movieRepository.getTvShowById(tvId)).thenReturn(tvShowDetail)

        viewModel.detailTvShowById.observeForever(tvObserver)
        verify(tvObserver).onChanged(tvResource)

    }
}