package pl.piechaczek.dawid.core.ui.di

import dagger.BindsInstance
import dagger.Component
import pl.piechaczek.dawid.core.data.di.ApiServiceModule
import pl.piechaczek.dawid.core.data.di.NetworkModule
import pl.piechaczek.dawid.core.data.service.NbpService
import pl.piechaczek.dawid.core.ui.navigation.MainNavigator
import retrofit2.Retrofit

@Component(modules = [CoreModule::class, ApiServiceModule::class, NetworkModule::class])
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance mainNavigator: MainNavigator): CoreComponent
    }

    fun provideRetrofit(): Retrofit

    fun provideNbpService(): NbpService

    fun provideMainNavigator(): MainNavigator
}