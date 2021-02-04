package com.something.subfirstjetpack.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.something.subfirstjetpack.databinding.FragmentMovieBinding
import com.something.subfirstjetpack.ui.adapter.AdapterMovie
import com.something.subfirstjetpack.viewmodel.ViewModelFactory
import com.something.subfirstjetpack.vo.Status

class MovieFragment : Fragment() {

    private var fragmentMovieBinding: FragmentMovieBinding? = null
    private lateinit var viewModel: MovieViewModel
    private val binding get() = fragmentMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null){
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            val adapter = AdapterMovie()
            viewModel.getMovies().observe(viewLifecycleOwner, { listMovie ->
                if (listMovie != null){
                    when(listMovie.status){
                        Status.LOADING -> binding?.pbMovie?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding?.pbMovie?.visibility = View.GONE
                            binding?.rvMovie?.visibility = View.VISIBLE
                            adapter.submitList(listMovie.data)
                        }
                        Status.ERROR -> {
                            binding?.pbMovie?.visibility = View.GONE
                            Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                binding?.rvMovie?.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    this.adapter = adapter
                }
            })
        }
    }
}