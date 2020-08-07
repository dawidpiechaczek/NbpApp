package pl.piechaczek.dawid.core.ui

import android.app.Application
import pl.piechaczek.dawid.core.ui.di.CoreComponentProvider
import timber.log.Timber

import timber.log.Timber.DebugTree

class NbpApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        CoreComponentProvider.createComponent()
    }
}