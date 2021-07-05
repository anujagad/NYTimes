package com.anusha.nytimes.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import  com.anusha.nytimes.utils.Result

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiError(val code: Int, val errorResponse: ErrorResponse) : Throwable(errorResponse.error?.message
        ?: "") {
    companion object {
        fun empty(message: String): ApiError {
            return ApiError(-1, ErrorResponse(message = message))
        }
    }
}

fun String.openInChromeTab(context: Context) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    //builder.setToolbarColor(color)
    customTabsIntent.launchUrl(context, this.toUri())
}

fun String.openInChrome(context: Context?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
    context?.startActivity(browserIntent)
}

fun AppCompatActivity.goToPlayStore(): Intent {
    return try {
        Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
    } catch (anfe: android.content.ActivityNotFoundException) {
        Intent(Intent.ACTION_VIEW, "https://play.google.com/store/apps/details?id=$packageName".toUri())
    }
}

infix fun <T : Any> Result<T>.ifResultSuccess(code: (T) -> Unit) {
    if (this is Result.Success) {
        code.invoke(this.data)
    }
}

suspend infix fun <T : Any> Result<T>.ifResultSuccessSusp(code: suspend (T) -> Unit) {
    if (this is Result.Success) {
        withContext(Dispatchers.Main) {
            code.invoke(this@ifResultSuccessSusp.data)
        }
    }
}

suspend infix fun <T : Any> Result<T>.ifResultSuccessWithoutMain(code: suspend (T) -> Unit) {
    if (this is Result.Success) {
        code.invoke(this.data)
    }
}

fun <T : Any> Result<T>.checkResult(success: (T) -> Unit, error: (Throwable) -> Unit) {
    if (this is Result.Success) {
        success.invoke(this.data)
    } else if (this is Result.Error) {
        error.invoke(this.exception)
    }
}

suspend fun <T : Any> Result<T>.checkResultSuspWithoutMain(success: suspend (T) -> Unit, error: suspend (ApiError) -> Unit, complete: suspend () -> Unit = {}) {
    when (this) {
        is Result.Success -> success.invoke(this.data)
        is Result.Error -> error.invoke(this.exception)
        is Result.Complete -> complete.invoke()
    }
}

suspend fun <T : Any> Result<T>.checkResultSusp(success: suspend (T) -> Unit, error: suspend (ApiError) -> Unit, complete: suspend () -> Unit = {}) {
    when (this) {
        is Result.Success -> withContext(Dispatchers.Main) {
            success.invoke(this@checkResultSusp.data)
        }
        is Result.Error -> withContext(Dispatchers.Main) {
            error.invoke(this@checkResultSusp.exception)
        }
        is Result.Complete -> withContext(Dispatchers.Main) {
            complete.invoke()
        }
    }
}
