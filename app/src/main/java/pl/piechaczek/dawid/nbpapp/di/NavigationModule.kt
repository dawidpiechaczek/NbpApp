package pl.piechaczek.dawid.nbpapp.di

import dagger.Binds
import dagger.Module
import pl.piechaczek.dawid.core.ui.navigation.MainNavigator
import pl.piechaczek.dawid.nbpapp.navigation.MainNavigatorImpl

@Module
abstract class NavigationModule {

    @Binds
    abstract fun MainNavigatorImpl.provideMainNavigator(): MainNavigator
}