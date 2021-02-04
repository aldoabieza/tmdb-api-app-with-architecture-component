package com.something.subfirstjetpack.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.something.subfirstjetpack.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = fragmentFavoriteBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
        binding?.viewPager?.adapter = sectionsPagerAdapter
        binding?.tabs?.setupWithViewPager(binding?.viewPager)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFavoriteBinding = null
    }
}