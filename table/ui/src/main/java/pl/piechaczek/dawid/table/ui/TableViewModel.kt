package pl.piechaczek.dawid.table.ui

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.piechaczek.dawid.table.data.usecase.TableUseCase
import pl.piechaczek.dawid.table.ui.model.map
import javax.inject.Inject

abstract class TableViewModel : ViewModel() {
    abstract fun observeEffects(): Observable<TableViewEffect>
    abstract fun observeState(): Observable<TableViewState>
    abstract fun onAction(action: TableViewAction): Completable
}

class DefaultTableViewModel @Inject constructor(
    private val tableUseCase: TableUseCase
) : TableViewModel() {

    private val effects = PublishSubject.create<TableViewEffect>()
    private val state = BehaviorSubject.create<TableViewState>()

    override fun observeEffects(): Observable<TableViewEffect> = effects.hide()

    override fun observeState(): Observable<TableViewState> = state.hide()

    override fun onAction(action: TableViewAction): Completable =
        when (action) {
            is TableViewAction.ShowToast -> showToast()
            is TableViewAction.SegmentChanged -> changeSegment(action.newItemIndex)
        }

    private fun changeSegment(newSegmentIndex: Int): Completable = Completable.fromCallable {
        state.onNext(TableViewState(newSegmentIndex))
    }

    private fun showToast(): Completable =
        tableUseCase.getTable('A')
            .flatMapCompletable {
                val tables = it.map { it.map() }
                effects.onNext(
                    TableViewEffect.ShowTable(tables)
                )
                Completable.complete()
            }
}