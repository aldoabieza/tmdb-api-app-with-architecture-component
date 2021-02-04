package com.something.subfirstjetpack.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.something.subfirstjetpack.databinding.FragmentTvShowBinding
import com.something.subfirstjetpack.ui.adapter.AdapterTvShow
import com.something.subfirstjetpack.viewmodel.ViewModelFactory
import com.something.subfirstjetpack.vo.Status

class TvShowFragment : Fragment() {

    private lateinit var binding : FragmentTvShowBinding
    private lateinit var viewModel: TvShowViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            val adapter = AdapterTvShow()

            viewModel.getTvShow().observe(viewLifecycleOwner, { listItem ->
                if (listItem != null){
                    when(listItem.status){
                        Status.LOADING -> binding.pbTv.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.pbTv.visibility = View.GONE
                            binding.rvTvshow.visibility = View.VISIBLE
                            adapter.submitList(listItem.data)
                        }
                        Status.ERROR -> {
                            binding.pbTv.visibility = View.GONE
                            Toast.makeText(requireActivity(), "Not Connected", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            with(binding.rvTvshow){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }

    }

}