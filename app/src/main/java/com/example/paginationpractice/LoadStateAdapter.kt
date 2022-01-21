package com.example.paginationpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationpractice.databinding.ItemListFooterBinding
import kotlinx.android.synthetic.main.item_list_footer.view.*

class LoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<com.example.paginationpractice.LoadStateAdapter.FooterViewholder>() {
    class FooterViewholder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {
        init {
            itemView.retry_button.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            itemView.error_msg.isVisible != (loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            itemView.error_msg.text = (loadState as? LoadState.Error)?.error?.message
            itemView.retry_button.isVisible = loadState is LoadState.Error
            itemView.progress_bar.isVisible = loadState is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): FooterViewholder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
                return FooterViewholder(view, retry)
            }
        }
    }

    override fun onBindViewHolder(holder: FooterViewholder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewholder {
        return FooterViewholder.create(parent, retry)
    }
}