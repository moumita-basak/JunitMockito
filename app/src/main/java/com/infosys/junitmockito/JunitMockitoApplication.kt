package com.infosys.junitmockito

import android.app.Application
import android.content.Context
import com.infosys.junitmockito.network.MyApi
import com.infosys.junitmockito.network.NetworkConnectionInterceptor
import com.infosys.junitmockito.repositories.ItemRepository
import com.infosys.junitmockito.ui.ItemViewModelFactory

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class JunitMockitoApplication : Application(), KodeinAware {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Logger.addLogAdapter(AndroidLogAdapter())

    }

    companion object {
        var appContext: Context? = null
            private set
        var sDefSystemLanguage: String? = null
    }
    override val kodein = Kodein.lazy {
        import(androidXModule(this@JunitMockitoApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }

        bind() from provider { ItemViewModelFactory(instance(),instance()) }
        bind() from singleton { ItemRepository(instance(),instance(),instance()) }

    }
}