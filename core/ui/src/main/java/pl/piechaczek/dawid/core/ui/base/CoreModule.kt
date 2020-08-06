package pl.piechaczek.dawid.core.ui.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }
}