package pl.piechaczek.dawid.details.ui.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.piechaczek.dawid.core.ui.base.ViewModelKey
import pl.piechaczek.dawid.details.ui.root.DefaultDetailsViewModel
import pl.piechaczek.dawid.details.ui.root.DetailsViewModel

@Module
internal abstract class DetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DefaultDetailsViewModel): ViewModel

}