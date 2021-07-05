package com.anusha.nytimes.utils

import com.anusha.nytimes.viewModels.PopularArticlesListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object MainActivityModule {

    val module = module {
        viewModel { PopularArticlesListViewModel() }
    }
}