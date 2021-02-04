package com.something.subfirstjetpack.ui.favorite.tvshow

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
import com.something.subfirstjetpack.databinding.FragmentFavoriteTvShowBinding
import com.something.subfirstjetpack.ui.adapter.AdapterTvShow
import com.something.subfirstjetpack.viewmodel.ViewModelFactory

class FavoriteTvShowFragment : Fragment() {

    private var fragmentFavoriteTvShowBinding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = fragmentFavoriteTvShowBinding
    private lateinit var viewModel: BookmarkTvViewModel
    private lateinit var adapterBookmarkTvShow: AdapterTvShow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteTvShowBinding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvBookmarkTv)
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[BookmarkTvViewModel::class.java]
            adapterBookmarkTvShow = AdapterTvShow()
            binding?.pbBookmarkTv?.visibility = View.VISIBLE
            viewModel.getAllTvBookmark().observe(viewLifecycleOwner, { listTvBookmark ->
                if (listTvBookmark != null){
                    binding?.pbBookmarkTv?.visibility = View.GONE
                    binding?.rvBookmarkTv?.visibility = View.VISIBLE
                    adapterBookmarkTvShow.submitList(listTvBookmark)
                    adapterBookmarkTvShow.notifyDataSetChanged()
                }
            })
            binding?.rvBookmarkTv?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapterBookmarkTvShow
            }

        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val position = viewHolder.adapterPosition
                val favoriteMovieEntity = adapterBookmarkTvShow.getSwipedData(position)
                favoriteMovieEntity?.let { viewModel.setBookmarkTvShow(it) }

                val showBar = Snackbar.make(view as View, R.string.undo, Snackbar.LENGTH_LONG)
                showBar.setAction(R.string.ok){ v ->
                    favoriteMovieEntity?.let { viewModel.setBookmarkTvShow(it) }
                }
                showBar.show()
            }
        }
    })

}