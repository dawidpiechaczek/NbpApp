package pl.piechaczek.dawid.table.ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.piechaczek.dawid.core.ui.base.ViewModelKey
import pl.piechaczek.dawid.table.ui.DefaultTableViewModel

@Module
abstract class TableModule {

    @Binds
    @IntoMap
    @ViewModelKey(DefaultTableViewModel::class)
    abstract fun bindTableViewModel(viewModel: DefaultTableViewModel): ViewModel
}