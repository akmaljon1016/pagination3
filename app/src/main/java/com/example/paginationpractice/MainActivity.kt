package com.example.paginationpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list_footer.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    lateinit var pagingAdapter: PagingAdapter
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pagingAdapter = PagingAdapter()
        recyclerview.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { pagingAdapter.retry() },
            footer = LoadStateAdapter { pagingAdapter.retry() }
        )
        pagingAdapter.addLoadStateListener { loadState ->
            recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
            progresbar.isVisible = loadState.source.refresh is LoadState.Loading
            button.isVisible = loadState.source.refresh is LoadState.Error
        }
        button.setOnClickListener { pagingAdapter.retry() }
        lifecycleScope.launch {
            mainViewModel.listData.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerview.scrollToPosition(0) }
        }

//            mainViewModel.listData.asLiveData().observe(this, Observer {
//                Toast.makeText(this@MainActivity,it.toString(), Toast.LENGTH_SHORT).show()
//            })

    }
}