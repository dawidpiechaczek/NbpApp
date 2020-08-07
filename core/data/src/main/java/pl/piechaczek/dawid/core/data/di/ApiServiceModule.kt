package pl.piechaczek.dawid.core.data.di

import dagger.Module
import dagger.Provides
import pl.piechaczek.dawid.core.data.service.NbpService
import retrofit2.Retrofit

@Module
class ApiServiceModule {

    @Provides
    fun provideNbpService(retrofit: Retrofit): NbpService {
        return retrofit.create(NbpService::class.java)
    }
}