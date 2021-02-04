package com.something.subfirstjetpack.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.something.subfirstjetpack.R
import com.something.subfirstjetpack.ui.favorite.movie.FavoriteMovieFragment
import com.something.subfirstjetpack.ui.favorite.tvshow.FavoriteTvShowFragment

class SectionsPagerAdapter(private val ctx: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tshow)
    }

    override fun getCount(): Int = TAB_TITLES.size

    override fun getItem(position: Int): Fragment =
            when(position){
                0 -> FavoriteMovieFragment()
                1 -> FavoriteTvShowFragment()
                else -> Fragment()
            }

    override fun getPageTitle(position: Int): CharSequence  = ctx.resources.getString(TAB_TITLES[position])
}
