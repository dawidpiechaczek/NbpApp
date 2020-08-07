package pl.piechaczek.dawid.table.ui.di

import pl.piechaczek.dawid.table.ui.TableFragment

internal object ComponentProvider {

    private var component: TableComponent? = null

    fun createComponent(fragment: TableFragment): TableComponent {
        component = DaggerTableComponent.factory()
            .create(fragment)
        return component!!
    }

    fun getComponent(): TableComponent {

        if (component == null)
            throw IllegalStateException("Component must be initialized")

        return component!!
    }
}