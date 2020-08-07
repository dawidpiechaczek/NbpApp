package pl.piechaczek.dawid.core.ui

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.piechaczek.dawid.core.data.utils.ApiUrl

@Module
class CoreModule {

    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @ApiUrl
    fun provideApiUrl() = BuildConfig.API_URL
}