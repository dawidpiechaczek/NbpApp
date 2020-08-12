package pl.piechaczek.dawid.core.ui

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import pl.piechaczek.dawid.core.ui.di.CoreComponentProvider
import pl.piechaczek.dawid.core.ui.navigation.MainNavigator
import timber.log.Timber

import timber.log.Timber.DebugTree

class NbpApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        AndroidThreeTen.init(this)
    }

    fun provideMainNavigator(mainNavigator: MainNavigator) {
        CoreComponentProvider.createComponent(mainNavigator)
    }
}