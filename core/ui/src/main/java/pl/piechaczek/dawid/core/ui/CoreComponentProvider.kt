package pl.piechaczek.dawid.core.ui

object CoreComponentProvider {

    private lateinit var component: CoreComponent

    fun createComponent(): CoreComponent {

        component = DaggerCoreComponent
            .factory()
            .create()

        return component
    }

    fun getComponent(): CoreComponent {

        if (!::component.isInitialized)
            throw IllegalStateException("component should be initialized earlier")

        return component
    }
}