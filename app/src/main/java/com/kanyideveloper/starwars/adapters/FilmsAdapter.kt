package com.kanyideveloper.starwars.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.starwars.databinding.FilmRowBinding
import com.kanyideveloper.starwars.models.Film

class FilmsAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<Film, FilmsAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: FilmRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film?) {
            binding.textViewFilmName.text = film?.title
            binding.textViewOpeningCrawl.text = film?.openingCrawl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            FilmRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(character!!)
        }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem == newItem
            }
        }
    }

    class OnClickListener(val clickListener: (result: Film) -> Unit) {
        fun onClick(result: Film) = clickListener(result)
    }
}