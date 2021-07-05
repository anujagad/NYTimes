package com.anusha.nytimes.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anusha.nytimes.R
import com.anusha.nytimes.adapter.PopularArticlesListAdapter
import com.anusha.nytimes.viewModels.PopularArticlesListViewModel
import kotlinx.android.synthetic.main.fragment_popular_articles_list.*

class PopularArticlesListFragment : BaseFragment(){
    override val layoutId: Int = R.layout.fragment_popular_articles_list
    private val viewModel: PopularArticlesListViewModel by  activityViewModels()


    private var popularArticlesListAdapter = PopularArticlesListAdapter().setItemListener { artcile->
        viewModel.selectedArticle.value = artcile

        if (findNavController().currentDestination?.id == R.id.loadPopularArticlesListFragment) {
            val directions = PopularArticlesListFragmentDirections.openArticlesDetailFragment()
            findNavController().navigate(directions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.invalidateOptionsMenu()
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initViews()
        viewModel.loadPopularArtiles()

        viewModel.formatedPopularArticles.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                popularArticlesListAdapter.setList(list)
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { message ->
            tvError.text =  message
        })
    }

    private fun setupRecyclerView() {
        rvPopularArticles.apply {
            adapter = popularArticlesListAdapter
        }
    }

    private fun initViews() {

        swipeRefreshList.apply {
            setOnRefreshListener {
                swipeRefreshList.isRefreshing = false
                viewModel.loadPopularArtiles()
            }
        }
    }

}