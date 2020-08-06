package pl.piechaczek.dawid.nbpapp.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.piechaczek.dawid.nbpapp.di.DaggerAppComponent

class NbpApp : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}