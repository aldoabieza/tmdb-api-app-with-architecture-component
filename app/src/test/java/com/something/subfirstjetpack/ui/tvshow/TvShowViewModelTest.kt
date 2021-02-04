package com.something.subfirstjetpack.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.something.subfirstjetpack.data.MovieRepository
import com.something.subfirstjetpack.data.source.local.entity.TvShowEntity
import com.something.subfirstjetpack.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowEntity>

    @Before
    fun setUp(){
        viewModel = TvShowViewModel(movieRepository)
    }

    @Test
    fun getTvShow() {

        val dummyTvShow = Resource.success(tvShowPagedList)
        `when`(dummyTvShow.data?.size).thenReturn(20)
        val tvShow = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShow.value = dummyTvShow

        `when`(movieRepository.getAllTvShow()).thenReturn(tvShow)
        val tvEntities = viewModel.getTvShow().value?.data
        verify(movieRepository).getAllTvShow()
        assertNotNull(tvEntities)
        assertEquals(20, tvEntities?.size)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)

    }
}