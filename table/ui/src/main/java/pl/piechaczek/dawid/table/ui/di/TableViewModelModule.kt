package pl.piechaczek.dawid.table.ui.di

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.piechaczek.dawid.table.ui.table.TableViewEffect
import pl.piechaczek.dawid.table.ui.table.TableViewState

@Module
class TableViewModelModule {

    @Provides
    internal fun provideTableEffect(): PublishSubject<TableViewEffect> =
        PublishSubject.create<TableViewEffect>()

    @Provides
    internal fun provideTableState(): BehaviorSubject<TableViewState> =
        BehaviorSubject.create<TableViewState>()

}