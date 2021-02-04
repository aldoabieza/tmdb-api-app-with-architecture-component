package com.something.subfirstjetpack.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.something.subfirstjetpack.R
import com.something.subfirstjetpack.data.source.local.entity.MovieEntity
import com.something.subfirstjetpack.databinding.ItemsKatalogBinding
import com.something.subfirstjetpack.ui.action.ItemClickCallback
import com.something.subfirstjetpack.ui.detail.DetailActivity

class AdapterMovie : PagedListAdapter<MovieEntity, AdapterMovie.CardViewHolder>(DIFF_CALLBACK){

    companion object{

        private const val IMG_URL = "https://image.tmdb.org/t/p/w500"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                    oldItem == newItem

        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemsKatalogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val movies = getItem(position)
        movies?.let { holder.bind(it) }
    }

    inner class CardViewHolder(private val binding: ItemsKatalogBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity){
            with(binding){
                tvTitle.text = movie.name
                tvRelease.text = movie.firstAirDate
                tvDescription.text = movie.overview

                itemView.setOnClickListener(ItemClickCallback(object : ItemClickCallback.OnItemClickCallback{
                    override fun onItemClicked(v: View) {
                        val moveToDetail = Intent(itemView.context, DetailActivity::class.java)
                        moveToDetail.putExtra(DetailActivity.EXTRA_MOVIE, movie.id.toString())
                        itemView.context.startActivity(moveToDetail)
                    }
                }))

                Glide.with(itemView.context)
                        .load("$IMG_URL${movie.posterPath}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_replay_24)
                                .error(R.drawable.ic_baseline_error_24))
                        .into(binding.imgList)
            }
        }
    }
}