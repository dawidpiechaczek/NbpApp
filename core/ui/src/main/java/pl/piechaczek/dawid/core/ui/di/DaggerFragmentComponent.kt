package pl.piechaczek.dawid.core.ui.di

import dagger.BindsInstance
import pl.piechaczek.dawid.core.ui.base.BaseFragment

abstract class DaggerFragmentComponent<in TFragment : BaseFragment<*, *>> {

    abstract class Factory<TComponent : DaggerFragmentComponent<*>, TFragment : BaseFragment<*, *>> {
        abstract fun create(
            ATVCoreComponent: CoreComponent,
            @BindsInstance fragment: TFragment
        ): TComponent

        abstract fun injectFragment(fragment: TFragment, component: TComponent)
        fun create(fragment: TFragment): TComponent {
            val component = create(fragment.coreComponent, fragment)
            injectFragment(fragment, component)
            return component
        }
    }

    abstract fun inject(fragment: TFragment)
}