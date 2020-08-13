package pl.piechaczek.dawid.table.ui.di

import dagger.Component
import pl.piechaczek.dawid.core.ui.di.ViewModelFactoryModule
import pl.piechaczek.dawid.core.ui.di.CoreComponent
import pl.piechaczek.dawid.core.ui.di.DaggerFragmentComponent
import pl.piechaczek.dawid.table.data.di.TableUseCaseModule
import pl.piechaczek.dawid.table.ui.table.TableFragment
import pl.piechaczek.dawid.table.ui.root.SegmentedTableFragment

@Component(
    dependencies = [CoreComponent::class],
    modules = [
        TableModule::class,
        TableUseCaseModule::class,
        TableViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
internal abstract class TableComponent : DaggerFragmentComponent<SegmentedTableFragment>() {

    @Component.Factory
    abstract class Factory :
        DaggerFragmentComponent.Factory<TableComponent, SegmentedTableFragment>() {

        override fun injectFragment(fragment: SegmentedTableFragment, component: TableComponent) {
            component.inject(fragment)
        }
    }

    abstract fun inject(fragment: TableFragment)
}
