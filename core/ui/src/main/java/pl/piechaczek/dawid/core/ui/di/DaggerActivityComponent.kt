package pl.piechaczek.dawid.core.ui.di

import dagger.BindsInstance
import pl.piechaczek.dawid.core.ui.base.BaseActivity

abstract class DaggerActivityComponent<in TActivity : BaseActivity<*, *>> {

    abstract class Factory<TComponent : DaggerActivityComponent<*>, TActivity : BaseActivity<*, *>> {
        abstract fun create(
            ATVCoreComponent: CoreComponent,
            @BindsInstance activity: TActivity
        ): TComponent

        abstract fun injectActivity(activity: TActivity, component: TComponent)
        fun create(activity: TActivity): TComponent {
            val component = create(activity.coreComponent, activity)
            injectActivity(activity, component)
            return component
        }
    }

    abstract fun inject(activity: TActivity)
}