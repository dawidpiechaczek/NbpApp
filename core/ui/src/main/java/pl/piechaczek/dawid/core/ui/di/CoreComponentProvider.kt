package pl.piechaczek.dawid.core.ui.di

import pl.piechaczek.dawid.core.ui.navigation.MainNavigator

object CoreComponentProvider {

    private lateinit var component: CoreComponent

    fun createComponent(mainNavigator: MainNavigator): CoreComponent {

        component = DaggerCoreComponent.factory()
            .create(mainNavigator)

        return component
    }

    fun getComponent(): CoreComponent {

        if (!CoreComponentProvider::component.isInitialized)
            throw IllegalStateException("component should be initialized earlier")

        return component
    }
}