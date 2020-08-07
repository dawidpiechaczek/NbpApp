package pl.piechaczek.dawid.nbpapp.di

import dagger.Component
import pl.piechaczek.dawid.core.ui.CoreComponent
import pl.piechaczek.dawid.core.ui.DaggerActivityComponent
import pl.piechaczek.dawid.core.ui.base.ViewModelFactoryModule
import pl.piechaczek.dawid.nbpapp.main.MainActivity

@Component(
    dependencies = [CoreComponent::class],
    modules = [
        MainModule::class,
        ViewModelFactoryModule::class
    ]
)
abstract class MainComponent : DaggerActivityComponent<MainActivity>() {

    @Component.Factory
    abstract class Factory : DaggerActivityComponent.Factory<MainComponent, MainActivity>() {

        override fun injectActivity(activity: MainActivity, component: MainComponent) {
            component.inject(activity)
        }
    }
}