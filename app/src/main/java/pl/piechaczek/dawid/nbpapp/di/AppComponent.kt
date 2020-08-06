package pl.piechaczek.dawid.nbpapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.piechaczek.dawid.core.ui.base.CoreModule
import pl.piechaczek.dawid.core.ui.base.ViewModelFactoryModule
import pl.piechaczek.dawid.nbpapp.app.NbpApp

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        ViewModelFactoryModule::class,
        CoreModule::class
    ]
)
interface AppComponent : AndroidInjector<NbpApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}