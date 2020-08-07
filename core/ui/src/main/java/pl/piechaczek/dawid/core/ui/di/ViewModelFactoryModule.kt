package pl.piechaczek.dawid.core.ui.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import pl.piechaczek.dawid.core.ui.base.ViewModelFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun ViewModelFactory.bindViewModelFactory(): ViewModelProvider.Factory
}