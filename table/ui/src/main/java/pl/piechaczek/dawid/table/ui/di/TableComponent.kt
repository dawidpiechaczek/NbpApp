package pl.piechaczek.dawid.table.ui.di

import dagger.Component
import pl.piechaczek.dawid.core.ui.CoreComponent
import pl.piechaczek.dawid.core.ui.DaggerFragmentComponent
import pl.piechaczek.dawid.core.ui.base.ViewModelFactoryModule
import pl.piechaczek.dawid.table.data.di.TableUseCaseModule
import pl.piechaczek.dawid.table.ui.TableFragment

@Component(
    dependencies = [CoreComponent::class],
    modules = [
        TableModule::class,
        TableUseCaseModule::class,
        ViewModelFactoryModule::class
    ]
)
abstract class TableComponent : DaggerFragmentComponent<TableFragment>() {

    @Component.Factory
    abstract class Factory : DaggerFragmentComponent.Factory<TableComponent, TableFragment>() {

        override fun injectFragment(fragment: TableFragment, component: TableComponent) {
            component.inject(fragment)
        }
    }
}
