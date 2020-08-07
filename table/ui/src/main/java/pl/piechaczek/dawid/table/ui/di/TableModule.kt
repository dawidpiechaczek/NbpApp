package pl.piechaczek.dawid.table.ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.piechaczek.dawid.core.ui.base.ViewModelKey
import pl.piechaczek.dawid.table.ui.table.DefaultTableViewModel
import pl.piechaczek.dawid.table.ui.table.TableViewModel
import pl.piechaczek.dawid.table.ui.root.DefaultSegmentedViewModel
import pl.piechaczek.dawid.table.ui.root.SegmentedTableViewModel

@Module
internal abstract class TableModule {

    @Binds
    @IntoMap
    @ViewModelKey(TableViewModel::class)
    abstract fun bindTableViewModel(viewModel: DefaultTableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SegmentedTableViewModel::class)
    abstract fun bindSegmentedTableViewModel(viewModel: DefaultSegmentedViewModel): ViewModel
}