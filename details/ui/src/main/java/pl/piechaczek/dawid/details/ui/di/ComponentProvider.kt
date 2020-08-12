package pl.piechaczek.dawid.details.ui.di

import pl.piechaczek.dawid.details.ui.root.DetailsFragment

internal object ComponentProvider {

    private var component: DetailsComponent? = null

    fun createComponent(fragment: DetailsFragment): DetailsComponent {
        component = DaggerDetailsComponent.factory()
            .create(fragment)
        return component!!
    }

    fun getComponent(): DetailsComponent {

        if (component == null)
            throw IllegalStateException("Component must be initialized")

        return component!!
    }
}