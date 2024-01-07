package com.poc.photosapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.databinding.CardItemBinding

class PhotosAdapter(private val listener: OnItemClicked) :
    ListAdapter<PhotoItem, PhotosAdapter.ViewHolder>(DIFF) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }


    interface OnItemClicked {
        fun onItemClicked(photo: PhotoItem)
    }

    inner class ViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindItem(item: PhotoItem) {
            with(binding) {
                root.setOnClickListener {
                    listener.onItemClicked(getItem(adapterPosition))
                }
                photo = item
            }
        }
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<PhotoItem>() {
            override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean =
                oldItem == newItem

        }


    }

}