package pl.piechaczek.dawid.nbpapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.piechaczek.dawid.core.ui.base.ViewModelKey
import pl.piechaczek.dawid.nbpapp.main.DefaultMainViewModel

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(DefaultMainViewModel::class)
    abstract fun bindMainViewModel(viewModel: DefaultMainViewModel): ViewModel
}