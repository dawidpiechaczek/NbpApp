package pl.piechaczek.dawid.details.ui.di

import dagger.Component
import pl.piechaczek.dawid.core.ui.di.CoreComponent
import pl.piechaczek.dawid.core.ui.di.DaggerFragmentComponent
import pl.piechaczek.dawid.core.ui.di.ViewModelFactoryModule
import pl.piechaczek.dawid.details.ui.root.DetailsFragment

@DetailsScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        DetailsModule::class,
        ViewModelFactoryModule::class
    ]
)
internal abstract class DetailsComponent : DaggerFragmentComponent<DetailsFragment>() {

    @Component.Factory
    abstract class Factory :
        DaggerFragmentComponent.Factory<DetailsComponent, DetailsFragment>() {

        override fun injectFragment(fragment: DetailsFragment, component: DetailsComponent) {
            component.inject(fragment)
        }
    }
}