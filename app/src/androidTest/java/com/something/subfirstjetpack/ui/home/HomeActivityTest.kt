package com.something.subfirstjetpack.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.something.subfirstjetpack.R
import com.something.subfirstjetpack.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest{

    @Before
    fun setUp(){
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadListMovies(){
        onView(withId(R.id.rv_movie)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        onView(withId(R.id.detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_release)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_vote)).check(matches(isDisplayed()))
    }

    @Test
    fun actionBookmarkMovies(){
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rv_bookmark_movie)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        onView(withId(R.id.detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_release)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_vote)).check(matches(isDisplayed()))
    }

    @Test
    fun loadListTv(){
        onView(withId(R.id.tvShowFragment)).perform(click())
        onView(withId(R.id.rv_tvshow)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        onView(withId(R.id.detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_release)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_vote)).check(matches(isDisplayed()))
    }

    @Test
    fun actionBookmarkTvShow(){
        onView(withId(R.id.tvShowFragment)).perform(click())
        onView(withId(R.id.rv_tvshow)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        onView(withId(R.id.action_bookmark)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_bookmark_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).perform(swipeDown())
        onView(withId(R.id.rv_bookmark_tv))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_release)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_vote)).check(matches(isDisplayed()))
    }

}