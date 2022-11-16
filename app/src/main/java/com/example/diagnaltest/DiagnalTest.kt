package com.example.diagnaltest

import android.app.Application
import com.example.diagnaltest.model.ListingRepositoryImpl
import com.example.diagnaltest.repository.ListingRepository
import com.example.diagnaltest.viewmodel.ContentListingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class DiagnalTest : Application() {
    private val appModule = module {
        single<ListingRepository> { ListingRepositoryImpl(get()) }
        viewModel { ContentListingViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DiagnalTest)
            modules(appModule)
        }
    }
}