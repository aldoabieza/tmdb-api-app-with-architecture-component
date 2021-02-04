package com.something.subfirstjetpack.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.something.subfirstjetpack.R
import com.something.subfirstjetpack.databinding.FragmentFavoriteMovieBinding
import com.something.subfirstjetpack.ui.adapter.AdapterMovie
import com.something.subfirstjetpack.viewmodel.ViewModelFactory

class FavoriteMovieFragment : Fragment() {

    private var fragmentFavoriteMovieBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = fragmentFavoriteMovieBinding
    private lateinit var viewModel: BookmarkMovieViewModel
    private lateinit var adapterBookmarkMovie: AdapterMovie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        fragmentFavoriteMovieBinding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvBookmarkMovie)
        if (activity != null){
            adapterBookmarkMovie = AdapterMovie()
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[BookmarkMovieViewModel::class.java]
            binding?.pbBookmarkMovie?.visibility = View.VISIBLE
            viewModel.getAllBookmarkMovies().observe(viewLifecycleOwner, { listMovieFavorite ->
                binding?.pbBookmarkMovie?.visibility = View.GONE
                binding?.rvBookmarkMovie?.visibility = View.VISIBLE
                adapterBookmarkMovie.submitList(listMovieFavorite)
                adapterBookmarkMovie.notifyDataSetChanged()
            })
            binding?.rvBookmarkMovie?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapterBookmarkMovie
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.adapterPosition
                val favoriteMovieEntity = adapterBookmarkMovie.getSwipedData(swipedPosition)
                favoriteMovieEntity?.let { viewModel.setBookmarkMovie(it) }

                val showBar = Snackbar.make(view as View, R.string.undo, Snackbar.LENGTH_LONG)
                showBar.setAction(R.string.ok){ v ->
                    favoriteMovieEntity?.let { viewModel.setBookmarkMovie(it) }
                }
                showBar.show()
            }
        }
    })
}