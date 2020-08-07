package pl.piechaczek.dawid.table.data.di

import dagger.Module
import dagger.Provides
import pl.piechaczek.dawid.core.data.service.NbpService
import pl.piechaczek.dawid.table.data.service.TableService
import pl.piechaczek.dawid.table.data.service.TableServiceImpl
import pl.piechaczek.dawid.table.data.usecase.TableUseCase

@Module
class TableUseCaseModule {

    @Provides
    fun providesTableUseCase(service: TableService): TableUseCase =
        TableUseCase(service)

    @Provides
    fun provideTableService(nbpService: NbpService): TableService =
        TableServiceImpl(nbpService)
}