package pl.piechaczek.dawid.details.data.di

import dagger.Module
import dagger.Provides
import pl.piechaczek.dawid.core.data.service.NbpService
import pl.piechaczek.dawid.details.data.usecases.DetailsUseCase
import pl.piechaczek.dawid.details.data.services.DetailsService
import pl.piechaczek.dawid.details.data.services.DetailsServiceImpl

@Module
class DetailsUseCaseModule {

    @Provides
    fun providesTableUseCase(service: DetailsService): DetailsUseCase =
        DetailsUseCase(service)

    @Provides
    fun provideTableService(nbpService: NbpService): DetailsService =
        DetailsServiceImpl(nbpService)
}