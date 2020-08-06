package pl.piechaczek.dawid.nbpapp.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.piechaczek.dawid.nbpapp.BuildConfig
import pl.piechaczek.dawid.nbpapp.di.DaggerAppComponent
import timber.log.Timber

import timber.log.Timber.DebugTree

class NbpApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}