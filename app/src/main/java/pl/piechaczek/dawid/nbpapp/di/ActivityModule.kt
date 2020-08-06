package pl.piechaczek.dawid.nbpapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.piechaczek.dawid.nbpapp.main.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}