package pl.piechaczek.dawid.nbpapp.di

import pl.piechaczek.dawid.nbpapp.main.MainActivity


internal object ComponentProvider {

    private var component: MainComponent? = null

    fun createComponent(fragment: MainActivity): MainComponent {
        component = DaggerMainComponent.factory().create(fragment)
        return component!!
    }

    fun getComponent(): MainComponent {

        if (component == null)
            throw IllegalStateException("Component must be initialized")

        return component!!
    }
}