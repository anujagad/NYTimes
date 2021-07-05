package com.anusha.nytimes.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + parentJob

    private val parentJob = SupervisorJob()


    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    protected suspend fun launchOnUI(block: suspend () -> Unit) {
        withContext(Dispatchers.Main) {
            block()
        }
    }
}
