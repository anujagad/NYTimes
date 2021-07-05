package com.anusha.nytimes

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import com.anusha.nytimes.activity.MainActivity
import com.anusha.nytimes.network.DataProvider
import com.anusha.nytimes.utils.MainActivityModule
import com.jakewharton.threetenabp.AndroidThreeTen
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*


class App : Application() {
    companion object {

        lateinit var appScope: CoroutineScope
        const val UNAUTHORIZED_MESSAGE = "UNAUTHORIZED_MESSAGE"

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        initInternetChecker()
        initAndroidThreeTen()
        initCoroutineScope()
        initKoin()
        setupDataProviderCallbacks()

        DataProvider.getContext = {
            this
        }
    }

    private fun initCoroutineScope() {
        appScope = CoroutineScope(SupervisorJob())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    MainActivityModule.module
                )
            )
        }
    }

    private fun setupDataProviderCallbacks() {
        DataProvider.unauthorizedCallback = {
            val intent = Intent(this, MainActivity::class.java)
            intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            intent.putExtra(UNAUTHORIZED_MESSAGE, true)
            startActivity(intent)
        }
    }

    private fun initAndroidThreeTen() {
        AndroidThreeTen.init(this)
    }

    @Suppress("unused")
    private fun initStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    private fun initInternetChecker() {
        InternetAvailabilityChecker.init(this)
    }
}