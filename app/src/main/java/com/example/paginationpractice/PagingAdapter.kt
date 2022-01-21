package com.example.paginationpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paginationpractice.databinding.RecyclerItemBinding
import com.example.paginationpractice.model.New
import kotlinx.android.synthetic.main.recycler_item.view.*

class PagingAdapter : PagingDataAdapter<New, PagingAdapter.MyViewHolder>(diffCallback) {

    class MyViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: New?) {
            showData(item)
        }

        private fun showData(item: New?) {
            binding.title.text = item?.newsTitle
            binding.content.text = item?.newsContent
            binding.viewsEye.text = item?.totalViews.toString()
            binding.comment.text = item?.totalComment.toString()
            Glide.with(itemView).load(item?.newsImage)
                .into(binding.newsImage)
        }
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<New>() {
            override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
                return oldItem.newsId == newItem.newsId
            }

            override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title.setText(getItem(position)?.newsTitle)
        holder.bind(getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}